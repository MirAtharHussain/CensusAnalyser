package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population", required = true)
    public double population;

    @CsvBindByName(column = "Total Area", required = true)
    public double totalArea;

    @CsvBindByName(column = "population Density", required = true)
    public double populationDensity;

    public USCensusCSV() {
    }

    public USCensusCSV(String state, String stateCode, double population, double populationDensity, double totalArea) {
        this.state=state;
        this.stateId=stateCode;
        this.population=population;
        this.populationDensity=populationDensity;
        this.totalArea=totalArea;
    }
}