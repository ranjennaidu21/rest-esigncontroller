package com.esigncontroller.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountryRestRouteTest extends CamelTestSupport {

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private  ConsumerTemplate consumerTemplate;

    @Override
    protected RouteBuilder createRouteBuilder(){
    	//declare the java class to test
        return new CountryRestRoute();
    }

    @Autowired
    Environment environment;

    @Before
    public void setUp(){

    }

   @Test
   //test for insert query
    public void persistCountry(){
	   //hardcode the response that will receive from endpoint to use that and pass as input to this country endpoint
       String input = "{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":[\".uk\"],\"alpha2Code\":\"GB\",\"alpha3Code\":\"GBR\",\"callingCodes\":[\"44\"],\"capital\":\"London\",\"altSpellings\":[\"GB\",\"UK\",\"Great Britain\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":65110000,\"latlng\":[54.0,-2.0],\"demonym\":\"British\",\"area\":242900.0,\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC-02:00\",\"UTC\",\"UTC+01:00\",\"UTC+02:00\",\"UTC+06:00\"],\"borders\":[\"IRL\"],\"nativeName\":\"United Kingdom\",\"numericCode\":\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}],\"languages\":[{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Vereinigtes Königreich\",\"es\":\"Reino Unido\",\"fr\":\"Royaume-Uni\",\"ja\":\"イギリス\",\"it\":\"Regno Unito\",\"br\":\"Reino Unido\",\"pt\":\"Reino Unido\",\"nl\":\"Verenigd Koninkrijk\",\"hr\":\"Ujedinjeno Kraljevstvo\",\"fa\":\"بریتانیای کبیر و ایرلند شمالی\"},\"flag\":\"https://restcountries.eu/data/gbr.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GBR\"}";

       producerTemplate.requestBody("http://localhost:8081/country",input,String.class);
   }
   
   @Test
   //test for select query
    public void persistSelectCountry(){
       String input = "{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":[\".uk\"],\"alpha2Code\":\"GB\",\"alpha3Code\":\"GBR\",\"callingCodes\":[\"44\"],\"capital\":\"London\",\"altSpellings\":[\"GB\",\"UK\",\"Great Britain\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":65110000,\"latlng\":[54.0,-2.0],\"demonym\":\"British\",\"area\":242900.0,\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC-02:00\",\"UTC\",\"UTC+01:00\",\"UTC+02:00\",\"UTC+06:00\"],\"borders\":[\"IRL\"],\"nativeName\":\"United Kingdom\",\"numericCode\":\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}],\"languages\":[{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Vereinigtes Königreich\",\"es\":\"Reino Unido\",\"fr\":\"Royaume-Uni\",\"ja\":\"イギリス\",\"it\":\"Regno Unito\",\"br\":\"Reino Unido\",\"pt\":\"Reino Unido\",\"nl\":\"Verenigd Koninkrijk\",\"hr\":\"Ujedinjeno Kraljevstvo\",\"fa\":\"بریتانیای کبیر و ایرلند شمالی\"},\"flag\":\"https://restcountries.eu/data/gbr.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GBR\"}";

      String countryList =  producerTemplate.requestBody("http://localhost:8081/country",input,String.class);

       System.out.println("countryList : " + countryList);
       assertNotNull(countryList);
   }

}
