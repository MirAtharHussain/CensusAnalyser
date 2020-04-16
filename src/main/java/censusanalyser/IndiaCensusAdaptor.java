package censusanalyser;

import com.bridgelabz.CSVBuild.CSVBuilderException;
import com.bridgelabz.CSVBuild.CSVBuilderFactory;
import com.bridgelabz.CSVBuild.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class IndiaCensusAdaptor  extends CensusAdaptor{
    @Override
    public Map<String, CensusDAO> loadCensusData( String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        if (csvFilePath.length>1)
            return this.loadIndianStateCodeDataMap(censusMap,csvFilePath[1]);
        return super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);

    }

    private  Map<String,CensusDAO>  loadIndianStateCodeDataMap(Map<String,CensusDAO> censusMap,String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CensusAnalyserException("Enter proper file Extension",
                    CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            return censusMap;
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
}
