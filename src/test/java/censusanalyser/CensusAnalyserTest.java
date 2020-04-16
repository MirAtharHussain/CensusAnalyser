package censusanalyser;

import CommomCSV.CensusAnalyserCommonCSV;
import com.bridgelabz.CSVBuild.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String TYPE_INCORRECT_FILE = "./src/test/resources/IndiaStateCensusData.pdf";
    private static final String DElIMITER_HEADER_INCORRECT = "./src/test/resources/StateCensusData.csv";
    private static final String STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String STATE_CODE_CSV_WRONG_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String STATECODE_TYPE_INCORRECT = "./src/test/resources/IndiaStateCode.pdf";
    private static final String STATECODE_DELIMITER_HEADER = "./src/test/resources/StateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData (1).csv";


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenStateCensusCSVFile_TypeIncorrect_ReturnsCustomException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,TYPE_INCORRECT_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFile_WhenDelimiterIncorrect_ReturnCustomException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,DElIMITER_HEADER_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFile_WhenHeaderIncorrect_ReturnsCustomException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,DElIMITER_HEADER_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_ReturnNumberOf_CorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenWrongFile_ReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATE_CODE_CSV_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenFileTypeIncorrect_ReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATECODE_TYPE_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenDelimiterIncorrect_ReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATECODE_DELIMITER_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenHeaderIncorrect_ReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATECODE_DELIMITER_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_INCORRECTINFILE, e.type);
        }
    }

    @Test
    public void givenIndianCensusdata_WhenSortedState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusdata();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void commonCSVFile_ForCensusAnalyser() {
        CensusAnalyserCommonCSV censusAnalyserCommonCSV = new CensusAnalyserCommonCSV();
        censusAnalyserCommonCSV.loadCesusCommonCSV(INDIA_CENSUS_CSV_FILE_PATH);
    }

    @Test
    public void givenIndianStateCode_WhenSortedCode_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
           censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATE_CODE_CSV_FILE_PATH);
            String sortedStateData = censusAnalyser.getStateCodeSortedStatedataMap();
            IndiaStateCodeCSV[] stateCodeCSVS = new Gson().fromJson(sortedStateData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD", stateCodeCSVS[0].stateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCensusCSVFile_WhenProper_SortStatePopulationAndWriteToJsonFile() throws CensusAnalyserException, IOException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getPopulationWiseSortedState();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(199812341, censusCSV[0].population);
    }

    @Test
    public void givenStateCensusCSVFile_WhenProper_SortMostPopulationDensity() throws CensusAnalyserException, IOException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getPopulationDensityWiseSortedState();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(1102, censusCSV[0].populationDensity);
    }

    @Test
    public void givenStateCensusCSVFile_WhenProper_SortMostPopulationDensity_Area() throws CensusAnalyserException, IOException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getPopulationDensityAreaWiseSortedState();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(342239, censusCSV[0].totalArea);
    }

    @Test
    public void givenUsCensusData_ShouldReturnCorrectData() throws CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int usCensusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, usCensusDataCount);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSStateCensusCSVFile_WhenProper_SortStatePopulation() throws CensusAnalyserException, IOException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getPopulationWiseSortedState();
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals(3.7253956E7,censusCSV[0].population,1);
    }
}
