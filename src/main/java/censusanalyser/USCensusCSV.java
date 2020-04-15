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

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "state='" + state + '\'' +
                ", stateId='" + stateId + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
/*import com.google.gson.Gson;

        import java.util.Comparator;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.stream.Collectors;

public class CensusJsonBuilder {

    public String getStateCensusJson(Map censusMap, Comparator<CensusDAO> censusComparator) throws CensusAnalyserException {
        if(censusMap==null||censusMap.size()==0){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        List<CensusDAO> censusList= (List<CensusDAO>) censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusList,censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
    public String getCensusJson(Map censusMap, Comparator<CensusDAO> censusComparator) throws CensusAnalyserException {
        if(censusMap==null||censusMap.size()==0){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        List<CensusDAO> censusList= (List<CensusDAO>) censusMap.values().stream().collect(Collectors.toList());
        this.sortInAscendingOrder(censusList,censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    public String getStateCodeJson(Map stateCodeMap, Comparator<CensusDAO> censusComparator) throws CensusAnalyserException {
        if(stateCodeMap==null||stateCodeMap.size()==0){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        List<CensusDAO> stateCodeList= (List<CensusDAO>) stateCodeMap.values().stream().collect(Collectors.toList());
        stateCodeList=this.sortStateCode(stateCodeList,censusComparator);
        String sortedStateCensusJson = new Gson().toJson(stateCodeList);
        return sortedStateCensusJson;
    }
    public static void sort(List<CensusDAO> censusList, Comparator<CensusDAO> censusComparator) {
        for(int i=0;i<censusList.size()-1;i++){
            for(int j=0;j<censusList.size()-i-1;j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
    public static List sortStateCode( List<CensusDAO> stateCodeList,Comparator<CensusDAO> censusComparator) {
        for(int i=0;i<stateCodeList.size()-1;i++){
            for(int j=0;j<stateCodeList.size()-i-1;j++) {
                CensusDAO census1 = stateCodeList.get(j);
                CensusDAO census2 = stateCodeList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    stateCodeList.set(j, census2);
                    stateCodeList.set(j + 1, census1);
                }
            }
        }
        return stateCodeList;
    }
    public static void sortInAscendingOrder( List<CensusDAO> censusList,Comparator<CensusDAO> censusComparator) {
        for(int i=0;i<censusList.size()-1;i++){
            for(int j=0;j<censusList.size()-i-1;j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

}*/