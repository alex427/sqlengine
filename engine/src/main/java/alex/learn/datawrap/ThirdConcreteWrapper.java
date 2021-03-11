package alex.learn.datawrap;

import alex.learn.common.stmt.beans.ClauseMeta;
import alex.learn.common.stmt.beans.Column;
import alex.learn.common.stmt.beans.Func;
import alex.learn.common.wrapbeans.Chart;
import alex.learn.sqlexecute.utils.DataBaseClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 * 第三包装类：支持结果集落地到临时表
 */
public class ThirdConcreteWrapper extends ResultWrapper {

    DataBaseClient client = new DataBaseClient();

    ResultSet resultSet;
    ClauseMeta clauseMeta;
    String tmptable;
    String datasetId;

    Chart chart;

    public ThirdConcreteWrapper(ResultSet resultSet, ClauseMeta clauseMeta, String tmptable, String datasetId) {
        this.resultSet = resultSet;
        this.clauseMeta = clauseMeta;
        this.tmptable = tmptable;
        this.datasetId = datasetId;
    }

    //不接受参数，预留多层包装的空间
    public Chart wrap() throws Exception {
        //执行当前包装器，必须有维度
        List<Column> keys = this.clauseMeta.getSelectdims();
        int countd = keys.size();
        //执行当前包装器，必须有聚合函数，否则大批数据会被ETL到本地
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
            columns.add(func.getTalias());
        }

        //2.获取当前数据集字段类型
        List<String> typeinfo = new ArrayList<>();
        for (Column column : keys) {
            typeinfo.add(column.getColumnType());
        }
        for (Func func : funclist) {
            typeinfo.add(func.getColumnType());
        }

        //3.获取目标字段和值
        List<List<String>> values = new ArrayList<>();
        while (this.resultSet.next()) {
            List<String> single = new ArrayList<>();
            for (int i = 1; i <= countd; i++) {
                single.add(this.resultSet.getString(i));
            }
            for (int i = countd + 1; i <= (countd + countm); i++) {
                single.add(this.resultSet.getString(i));
            }
            values.add(single);
        }

        //String tablename = wrapAndInsert(columns,values,countd-1,tmptable,datasetId);
        String tablename = client.wrapAndInsert(columns, values, typeinfo, countd - 1, tmptable, datasetId);
        this.chart = new Chart();
        this.chart.setChartcode(tablename);
        client.closedbconnection();
        return this.chart;
    }

}
