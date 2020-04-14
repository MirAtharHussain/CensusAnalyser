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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList=null;
    List<IndiaStateCodeCSV> csvStateCodeList =new ArrayList<IndiaStateCodeCSV>();

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Enter proper file Extension",CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvfileIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            while (csvfileIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDAO(csvfileIterator.next()));
            }
            return  censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Enter delimiter in betwwen",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            /*csvStateCodeList = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);//using list to sort values
            return csvStateCodeList.size();*/
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
           /* while (stateCSVIterator.hasNext()) {
                csvStateCodeList.add(stateCSVIterator.next());
            }*/
            return this.getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Enter delimiter in betwwen",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = ()-> iterator;
       int numOfEnteries= (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
       return numOfEnteries;
    }

   /* public String getStateWiseSortedCensusdata(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList= csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census-> census.state);
            this.sort(censusCSVList,censusComparator);//in below code we removed censusCSVList bcz we made it global or instance variable
            String sortedStateCensusJson = new Gson().toJson(censusCSVList);
            return sortedStateCensusJson;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }*/
   public String getStateWiseSortedCensusdata(String csvFilePath) throws CensusAnalyserException {
            if (censusList==null || censusList.size()==0){
                throw new  CensusAnalyserException("No census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
           Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census-> census.state);
           this.sort(censusComparator);
           String sortedStateCensusJson = new Gson().toJson(censusList);
           return sortedStateCensusJson;
       }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i=0;i<censusList.size()-1;i++){
        for (int j=0;j<censusList.size()-i-1; j++){
            IndiaCensusDAO census1 = censusList.get(j);
            IndiaCensusDAO census2 = censusList.get(j+1);
            if (censusComparator.compare(census1,census2)>0){
                censusList.set(j,census2);
                censusList.set(j+1,census1);
            }

        }
    }
}

 /*   public String getCodeWiseSortedStateData(String stateCodeCsvFilePath) throws CensusAnalyserException {
        if (csvStateCodeList==null || csvStateCodeList.size()==0){
            throw new  CensusAnalyserException("No census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCodeCSV> stateCodeComparator = Comparator.comparing(census-> census.stateCode);
        this.sortCode(stateCodeComparator);
        String sortedStateCodeJson =new Gson().toJson(csvStateCodeList);
        return sortedStateCodeJson;

    }

    private void sortCode(Comparator<IndiaStateCodeCSV> stateCodeComparator) {
        for (int i=0;i<csvStateCodeList.size()-1;i++){
            for (int j=0;j<csvStateCodeList.size()-i-1; j++){
                if (csvStateCodeList.get(j).compareTo(csvStateCodeList.get(j + 1))) {
                    IndiaStateCodeCSV temp = csvStateCodeList.get(i);
                    csvStateCodeList.set(i, csvStateCodeList.get(j + 1));
                    csvStateCodeList.set(j + 1, csvStateCodeList.get(j));
                }
                }

            }
        }
    }*/
}

