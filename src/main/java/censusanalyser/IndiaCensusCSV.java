package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int totalArea;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int populationDensity;
    
    public IndiaCensusCSV() {
    }

    public IndiaCensusCSV(String state, double population, double populationDensity, double totalArea) {
        this.state=state;
        this.population= (int) population;
        this.populationDensity= (int) populationDensity;
        this.totalArea= (int) totalArea;
    }
}
