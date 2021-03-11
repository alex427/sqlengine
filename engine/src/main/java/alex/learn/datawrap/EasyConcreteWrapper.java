package alex.learn.datawrap;

import alex.learn.common.wrapbeans.Chart;
import alex.learn.common.wrapbeans.DataEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 * 简易包装类
 */
public class EasyConcreteWrapper extends ResultWrapper {

    ResultSet resultSet;
    String chartcode;
    Chart chart;

    public EasyConcreteWrapper(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.chartcode = "easy";
    }

    public Chart wrap() throws Exception {
        List<String> columns = new ArrayList<>();
        columns.add("max");
        columns.add("min");
        List<Map<String,String>> rows = new ArrayList<>();
        while (this.resultSet.next()) {
            List<String> onlydata = new ArrayList<>();
            Map<String,String> map = new HashMap<>();
            for (int i = 1; i <= 2; i++) {
                onlydata.add(this.resultSet.getString(i));
            }
            //存入K-V
            for (int i = 0; i <=1; i++) {
                map.put( columns.get(i), onlydata.get(i) );
            }
            rows.add(map);
        }
        this.chart = new Chart(this.chartcode,new DataEntity(columns,rows));
        return this.chart;
    }

}
