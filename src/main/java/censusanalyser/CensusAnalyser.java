package censusanalyser;


import com.bridgelabz.CSVBuild.CSVBuilderException;
import com.bridgelabz.CSVBuild.CSVBuilderFactory;
import com.bridgelabz.CSVBuild.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<CensusDAO> censusList = null;
    List<CensusDAO> stateList = null;
    SortedMap<String, CensusDAO> censusMap = null;
    SortedMap<String,CensusDAO> USCensusMap=null;
    SortedMap<String, CensusDAO> stateListMap = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<CensusDAO>();
        this.censusMap = new TreeMap<String, CensusDAO>();
        this.USCensusMap = new TreeMap<String, CensusDAO>();
        this.stateListMap = new TreeMap<String, CensusDAO>();
        this.stateList = new ArrayList<CensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }
    public int loadUsCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,USCensusCSV.class);
    }

    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvfileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<E> csvIterable = () -> csvfileIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }else  if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Enter delimiter in betwwen",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
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



    public int loadIndianStateCodeDataMap(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(censusCSV -> stateListMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            return stateListMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Enter delimiter in betwwen",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

    }

    public String getStateCodeSortedStatedataMap() throws CensusAnalyserException {
        if (stateListMap == null || stateListMap.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> stateCodeList=stateListMap.values().stream().collect(Collectors.toList());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        List list = this.sortInAscendingOrder(stateCodeList, censusComparator);
        String sortedStateCodeJson = new Gson().toJson(list);
        return sortedStateCodeJson;
    }

    public int loadIndiaPopulation_Density_AreaData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvfileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvfileIterator.hasNext()) {
                this.censusList.add(new CensusDAO(csvfileIterator.next()));
            }
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Enter delimiter in betwwen",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getPopulationWiseSortedState() throws CensusAnalyserException, IOException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sortingInDescendingOrder(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityWiseSortedState() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        this.sortingInDescendingOrder(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
    public String getPopulationDensityAreaWiseSortedState() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        this.sortingInDescendingOrder(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
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
    private void sortingInDescendingOrder(Comparator<CensusDAO> censusComparator) {
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
    }

}





