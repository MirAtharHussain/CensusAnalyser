package censusanalyser;


import com.bridgelabz.CSVBuild.CSVBuilderException;
import com.bridgelabz.CSVBuild.CSVBuilderFactory;
import com.bridgelabz.CSVBuild.ICSVBuilder;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
     SortedMap<String,IndiaCensusDAO> censusList=null;
     SortedMap<String,IndiaStateDAO>stateList=null;

    public CensusAnalyser() {
      this.censusList=new TreeMap<String, IndiaCensusDAO>();
      this.stateList=new TreeMap<String, IndiaStateDAO>();
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
                IndiaCensusCSV censusCSV = csvfileIterator.next();
                this.censusList.put(censusCSV.state,new IndiaCensusDAO(censusCSV));
            }
            return this.censusList.size();
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
                IndiaStateCodeCSV stateCodeCSV = stateCSVIterator.next();
                this.stateList.put(stateCodeCSV.stateCode,new IndiaStateDAO(stateCodeCSV));
            }
            return this.stateList.size();
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
        Object[] censusObjects = censusList.values().toArray();
        String sortedStateCensusJson = new Gson().toJson(censusObjects);
        return sortedStateCensusJson;
    }
    public String getStateCodeSortedStatedata() throws CensusAnalyserException {
        if (stateList == null || stateList.size() == 0) {
            throw new CensusAnalyserException("No census data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Object[] objects = stateList.values().toArray();
        String sortedStateCodeJson = new Gson().toJson(objects);
        return sortedStateCodeJson;
    }

}




