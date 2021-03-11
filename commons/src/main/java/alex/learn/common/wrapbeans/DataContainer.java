package alex.learn.common.wrapbeans;

import java.util.List;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/6/21
 */
public class DataContainer {

    private Chart chart;
    private Map<List<String>, List<String>> datamap;


    public DataContainer() {
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Map<List<String>, List<String>> getDatamap() {
        return datamap;
    }

    public void setDatamap(Map<List<String>, List<String>> datamap) {
        this.datamap = datamap;
    }

    //校验
    //逻辑还可以继续扩展
    public boolean verify(){
        return false;
    }
}
