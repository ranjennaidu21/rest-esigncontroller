package com.esigncontroller.routes;

import com.esigncontroller.domain.Country;
import com.esigncontroller.processor.BuildSQLProcessor;
import com.esigncontroller.processor.CountrySelectProcessor;
import org.postgresql.util.PSQLException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CountryRestRoute extends RouteBuilder{
	
    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    CountrySelectProcessor countrySelectProcessor;

    @Autowired
    BuildSQLProcessor buildSQLProcessor;
    
    @Override
    public void configure() throws Exception {
    	
        onException(PSQLException.class).log(LoggingLevel.ERROR,"PSQLException in the route ${body}")
        .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);
        
        
    	//This is post endpoint that will receive the route that we get as response from the country rest api
        GsonDataFormat countryFormat = new GsonDataFormat(Country.class);
        
        //address which we going to expose the rest api
        from("restlet:http://localhost:8081/country?restletMethods=POST").routeId("countryPostRoute")
                .log("Received Body is ${body}")
                .convertBodyTo(String.class)
                //convert json string to java object
                .unmarshal(countryFormat)
                //the body will now contain the country object
                .log("Unmarshaled record is ${body}")
        		//send this body response to database
		        .process(buildSQLProcessor)
		        //process the sql above and pass to db
		        .to("{{dbNode}}")
		        //select the inserted db
		        .to("{{selectNode}}")
		        .convertBodyTo(String.class)
		        .log("Inserted Country is ${body}");


    }
}
