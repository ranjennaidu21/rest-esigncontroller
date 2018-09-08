package com.esigncontroller.domain;

public class Country {
	//The name should be same with the value that will receive from json object
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    String alpha3Code;
    String population;

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", alpha3Code='" + alpha3Code + '\'' +
                ", population='" + population + '\'' +
                '}';
    }
}
