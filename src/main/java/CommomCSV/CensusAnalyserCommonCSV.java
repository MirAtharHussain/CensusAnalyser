package CommomCSV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CensusAnalyserCommonCSV {

       public void loadCesusCommonCSV(String csvFilePath)
       {
           try (
                    Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim());
            ) {
                for (CSVRecord csvRecord : csvParser) {
                    // Accessing values by Header namesState,Population,AreaInSqKm,DensityPerSqKm
                    String state = csvRecord.get("State");
                    String population = csvRecord.get("Population");
                    String areaInSqKm = csvRecord.get("AreaInSqKm");
                    String densityperSqKm = csvRecord.get("DensityperSqKm");

                    System.out.println("Record No - " + csvRecord.getRecordNumber());
                    System.out.println("---------------");
                    System.out.println("State : " + state);
                    System.out.println("population : " + population);
                    System.out.println("AreaInSqKm : " + areaInSqKm);
                    System.out.println("DensitySqKm : " + densityperSqKm);
                    System.out.println("---------------\n\n");
                }
            } catch (IOException e) {
               e.printStackTrace();
           }
       }

    }

