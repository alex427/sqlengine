package alex.learn.common.wrapbeans;

import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/21
 */
public class Chart {
    private String chartcode;
    private  DataEntity dataEntity;


    public Chart(String chartcode, DataEntity dataEntity) {
        this.chartcode = chartcode;
        this.dataEntity = dataEntity;
    }

    //支持临时表写入
    public Chart(String chartcode) {
        this.chartcode = chartcode;
    }

    private List<String> metadata;

    public Chart() {
    }

    public String getChartcode() {
        return chartcode;
    }

    public void setChartcode(String chartcode) {
        this.chartcode = chartcode;
    }

    public DataEntity getDataEntity() {
        return dataEntity;
    }

    public void setDataEntity(DataEntity dataEntity) {
        this.dataEntity = dataEntity;
    }

    public List<String> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<String> metadata) {
        this.metadata = metadata;
    }
}
