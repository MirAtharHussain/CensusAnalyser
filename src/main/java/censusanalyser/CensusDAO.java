package censusanalyser;

public class CensusDAO {

    public double population;
    public double populationDensity;
    public double totalArea;
    public String state;
    public String stateCode;


    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        this.state=indiaStateCodeCSV.state;
        this.stateCode=indiaStateCodeCSV.stateCode;
    }

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        totalArea=indiaCensusCSV.totalArea;
        populationDensity=indiaCensusCSV.populationDensity;
        population=indiaCensusCSV.population;

    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        state=usCensusCSV.state;
        stateCode=usCensusCSV.stateId;
        population= usCensusCSV.population;
        populationDensity=  usCensusCSV.populationDensity;
        totalArea= usCensusCSV.totalArea;

    }

    public Object getCensusDTO(CensusAnalyser.Country country,String... s) {
        if (s.length==1)
            return new IndiaStateCodeCSV(state,stateCode);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state,stateCode,population,populationDensity,totalArea);
        else
            return new IndiaCensusCSV(state,population,populationDensity,totalArea);

    }
}
