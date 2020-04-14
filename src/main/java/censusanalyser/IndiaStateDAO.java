package censusanalyser;

public class IndiaStateDAO {

    public   String stateCode;
    public   int tin;
    public int srNo;
    public String state;

    public IndiaStateDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        this.srNo=indiaStateCodeCSV.srNo;
        this.state=indiaStateCodeCSV.state;
        this.tin=indiaStateCodeCSV.tin;
        this.stateCode=indiaStateCodeCSV.stateCode;
    }

}
