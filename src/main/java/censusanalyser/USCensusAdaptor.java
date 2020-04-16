package censusanalyser;

import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return super.loadCensusData(USCensusCSV.class,csvFilePath[0]);
        }
    }

