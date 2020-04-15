package censusanalyser;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
   // List<CensusDAO> censusList = null;
    Map<String, CensusDAO> censusMap = null;

  /*  public CensusAnalyser() {
      //  this.censusList = new ArrayList<CensusDAO>();
     // this.censusMap = new HashMap<String, CensusDAO>();
    }*/

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap=new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        return censusMap.size();
    }
    public int loadUsCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap=new CensusLoader().loadCensusData(USCensusCSV.class, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseSortedCensusdata() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> stateCensusList=censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        List list = this.sortInAscendingOrder(stateCensusList, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    public String getStateCodeSortedStatedataMap() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> stateCodeList= censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        List list = this.sortInAscendingOrder(stateCodeList, censusComparator);
        String sortedStateCodeJson = new Gson().toJson(list);
        return sortedStateCodeJson;
    }

    public String getPopulationWiseSortedState() throws CensusAnalyserException, IOException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> statePopulationList=censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        List list = this.sortingInDescendingOrder(statePopulationList, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityWiseSortedState() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> statePopDensityList=censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        List list = this.sortingInDescendingOrder(statePopDensityList, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityAreaWiseSortedState() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> stateAreaList=censusMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        List list = this.sortingInDescendingOrder(stateAreaList, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }

    private List sortInAscendingOrder(List<CensusDAO> censusList, Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }

            }
        }
        return censusList;
    }
    private List sortingInDescendingOrder(List<CensusDAO> censusList,Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }

            }
        }
        return censusList;
    }

}





