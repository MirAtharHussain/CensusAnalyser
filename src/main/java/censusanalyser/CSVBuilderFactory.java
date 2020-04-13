package censusanalyser;

import com.opencsv.bean.IterableCSVToBeanBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCsvBuilder<>();
    }
}
