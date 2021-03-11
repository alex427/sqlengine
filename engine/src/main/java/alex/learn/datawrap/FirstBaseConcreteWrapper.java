package alex.learn.datawrap;

import alex.learn.common.wrapbeans.*;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 * 基础包装类
 */
public class FirstBaseConcreteWrapper extends ResultWrapper {

    ResultSet resultSet;
    String chartcode;
    ClauseMeta clauseMeta;
    Chart chart;

    public FirstBaseConcreteWrapper(ResultSet resultSet, ClauseMeta clauseMeta,String chartcode) {
        this.resultSet = resultSet;
        this.clauseMeta=clauseMeta;
        this.chartcode = chartcode;
    }

    //不接受参数，预留多层包装的空间
    public Chart wrap() throws Exception {
        List<Column> keys = this.clauseMeta.getSelectdims();
        int countd = keys.size();
        List<Func> funclist = this.clauseMeta.getFunclist();
        int countm = funclist.size();
        
        //1.获取当前数据集字段信息
        List<String> columns = new ArrayList<>();
        for (Column column : keys) {
            if (null != column.getTalias() && !"".equalsIgnoreCase(column.getTalias())) {
                columns.add(column.getTalias());
            } else {
                columns.add(column.getColumnName());
            }
        }
        for (Func func : funclist) {
            columns.add(func.getTalias() );
        }

        //2.获取目标字段和值
        List<Map<String,String>> rows = new ArrayList<>();
        while (this.resultSet.next()) {
            List<String> onlydata = new ArrayList<>();
            Map<String,String> map = new HashMap<>();
            for (int i = 1; i <= countd; i++) {
                onlydata.add(this.resultSet.getString(i));
            }
            for (int i = countd+1; i <= (countd+countm); i++) {
                onlydata.add(this.resultSet.getString(i));
            }
            //存入K-V
            for (int i = 0; i < (countd+countm); i++) {
                map.put( columns.get(i), onlydata.get(i) );
            }
            rows.add(map);
        }

        this.chart = new Chart(this.chartcode,new DataEntity(columns,rows));
        return this.chart;
    }

}
