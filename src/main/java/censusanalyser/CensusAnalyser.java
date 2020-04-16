package censusanalyser;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public enum Country{ INDIA,US};

    private Country country;

    Map<String, CensusDAO> censusMap = null;

   public CensusAnalyser(Country country) {
       this.censusMap=new HashMap<>();
       this.country=country;
   }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap=CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseSortedCensusdata() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        ArrayList censusDTOS= censusMap.values().stream().
                              sorted(censusComparator).
                              map(censusDAO -> censusDAO.getCensusDTO(country)).
                              collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }

    public String getStateCodeSortedStatedataMap() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        ArrayList censusDTOS= censusMap.values().stream().
                              sorted(censusComparator).
                              map(censusDAO -> censusDAO.getCensusDTO(country,"stateCode")).
                              collect(toCollection(ArrayList::new));
        String sortedStateCodeJson = new Gson().toJson(censusDTOS);
        return sortedStateCodeJson;
    }

    public String getPopulationWiseSortedState() throws CensusAnalyserException, IOException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        ArrayList censusDTOS= censusMap.values().stream().
                sorted(Collections.reverseOrder(censusComparator)).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityWiseSortedState() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        ArrayList censusDTOS= censusMap.values().stream().
                sorted(Collections.reverseOrder(censusComparator)).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityAreaWiseSortedState() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        ArrayList censusDTOS= censusMap.values().stream().
                              sorted(Collections.reverseOrder(censusComparator)).
                              map(censusDAO -> censusDAO.getCensusDTO(country)).
                              collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        System.out.println(sortedStateCensusJson);
        return sortedStateCensusJson;
    }
}





