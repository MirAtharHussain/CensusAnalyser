package censusanalyser;

public class IndiaCensusDAO {

    public int population;
    public int densityPerSqKm;
    public int areaInSqkm;
    public String state;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state=indiaCensusCSV.state;
        this.areaInSqkm=indiaCensusCSV.areaInSqKm;
        this.densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        this.population=indiaCensusCSV.population;

    }


}
