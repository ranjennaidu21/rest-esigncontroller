package com.esigncontroller.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esigncontroller.processor.CountrySelectProcessor;

@Component
public class RestCamelRoute extends RouteBuilder{

    @Autowired
    CountrySelectProcessor countrySelectProcessor;
    
    @Override
    public void configure() throws Exception {

        from("{{fromRoute}}")

        			.process(countrySelectProcessor)
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    //overwrite the to http uri below with the following, it passing
                    //the randomly generated country code retrieved from the countrySelectProcessor process method
                    .setHeader(Exchange.HTTP_URI,simple("https://restcountries.eu/rest/v2/alpha/${header.countryId}"))
        			.to("https://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
                    //.log("The REST COUNTRIES api response is ${body}");
        			//remove the http uri above
        			.removeHeader(Exchange.HTTP_URI)
        			//now it is a post call to submit param to the countrycode
                    .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        			//route to the post uri: /country
        			.to("{{toRoute}}");
        }
}
