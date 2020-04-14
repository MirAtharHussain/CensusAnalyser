package censusanalyser;


import com.bridgelabz.CSVBuild.CSVBuilderException;
import com.bridgelabz.CSVBuild.CSVBuilderFactory;
import com.bridgelabz.CSVBuild.ICSVBuilder;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList = null;
    List<IndiaStateDAO> stateList = null;
    SortedMap<String,IndiaStateDAO>stateList1=null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
        this.stateList = new ArrayList<IndiaStateDAO>();
        this.stateList1=new TreeMap<String, IndiaStateDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvfileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvfileIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDAO(csvfileIterator.next()));
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

    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (stateCSVIterator.hasNext()) {
                this.stateList.add(new IndiaStateDAO(stateCSVIterator.next()));
            }
            return stateList.size();
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
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    public String getStateCodeSortedStatedata() throws CensusAnalyserException {
        if (stateList == null || stateList.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort2(censusComparator);
        String sortedStateCodeJson = new Gson().toJson(stateList);
        return sortedStateCodeJson;
    }
    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) >0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }

            }
        }
    }
    public int loadIndianStateCodeDataMap(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (stateCSVIterator.hasNext()) {
                IndiaStateCodeCSV stateCodeCSV = stateCSVIterator.next();
                this.stateList1.put(stateCodeCSV.stateCode,new IndiaStateDAO(stateCodeCSV));
            }
            return this.stateList1.size();
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
        if (stateList1 == null || stateList1.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Object[] objects = stateList1.values().toArray();
        String sortedStateCodeJson = new Gson().toJson(objects);
        return sortedStateCodeJson;
    }


    public int loadIndiaPopulationData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvfileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvfileIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDAO(csvfileIterator.next()));
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
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sort2(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sort2(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }

            }
        }
    }
}




