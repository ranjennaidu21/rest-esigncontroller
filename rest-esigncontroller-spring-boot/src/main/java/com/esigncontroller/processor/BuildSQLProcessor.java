package com.esigncontroller.processor;


import com.esigncontroller.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildSQLProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
    	//Get the country object which unmarshalled from the CountryRestRoute
        Country country = (Country) exchange.getIn().getBody();
        //insert to db
        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO COUNTRY (NAME, COUNTRY_CODE, POPULATION) VALUES ('");
        builder.append(country.getName()+"','"+country.getAlpha3Code()+"',"+country.getPopulation()+");");

        //log.info("Query is :"+ builder.toString());

        exchange.getIn().setBody(builder.toString());
        //set countryId to be used in the select node from application.yml
        exchange.getIn().setHeader("countryId",country.getAlpha3Code());

    }
}
