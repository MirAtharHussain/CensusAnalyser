package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public int srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String state;

    @CsvBindByName(column = "Tin", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;


    public IndiaStateCodeCSV() {
    }

    public IndiaStateCodeCSV(String state, String stateCode) {
        this.state=state;
        this.stateCode=stateCode;
    }
}
