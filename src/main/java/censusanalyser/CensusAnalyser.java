package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        if (!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Enter proper file Extension",CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList= csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Enter delimiter in betwwen",CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CensusAnalyserException("Enter proper file Extension", CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return this.getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException("Enter delimiter in betwwen",CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = ()-> iterator;
       int numOfEnteries= (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
       return numOfEnteries;
    }

}

