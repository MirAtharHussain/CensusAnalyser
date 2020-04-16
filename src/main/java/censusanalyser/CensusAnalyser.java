package censusanalyser;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public enum Country {INDIA, US}
    private Country country;

    Map<String, CensusDAO> censusMap = null;

    public CensusAnalyser(Country country) {
        this.censusMap = new HashMap<>();
        this.country = country;
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusMap.size();
    }

    private void censusSizeCheck() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }

    public String getStateWiseSortedCensusdata() throws CensusAnalyserException {
         this.censusSizeCheck();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        ArrayList censusDTOS = censusMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }

    public String getStateCodeSortedStatedataMap() throws CensusAnalyserException {
        this.censusSizeCheck();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        ArrayList censusDTOS = censusMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country, "stateCode")).
                collect(toCollection(ArrayList::new));
        String sortedStateCodeJson = new Gson().toJson(censusDTOS);
        return sortedStateCodeJson;
    }

    public String getPopulationWiseSortedState() throws CensusAnalyserException, IOException {
        this.censusSizeCheck();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        return this.sortDescending(censusComparator);
    }

    public String getPopulationDensityWiseSortedState() throws CensusAnalyserException {
        this.censusSizeCheck();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        return this.sortDescending(censusComparator);
    }

    public String getPopulationDensityAreaWiseSortedState() throws CensusAnalyserException {
        this.censusSizeCheck();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        return this.sortDescending(censusComparator);
    }

    private String sortDescending(Comparator<CensusDAO> censusComparator) {
        ArrayList censusDTOS = censusMap.values().stream().
                sorted(Collections.reverseOrder(censusComparator)).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
}




