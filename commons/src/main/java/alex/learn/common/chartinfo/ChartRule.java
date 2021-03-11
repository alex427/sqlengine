package alex.learn.common.chartinfo;

import java.io.Serializable;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/7/6
 */
public class ChartRule implements Serializable {

    private String datasetId;
    private String tmptable;
    private String dbType;
    private List<PageColumn> columns;//目标字段
    //过滤器 当前需求一个图表一个过滤器， 但实际存在多个过滤器的情况，设置为list，预留扩展空间
    //如果支持多个过滤器，必须约束清楚过滤器之间的关系
    private List<Sfiltration> filters;
    private String handwriteWhere;
    private String handwriteHaving;

    public ChartRule() {
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getTmptable() {
        return tmptable;
    }

    public void setTmptable(String tmptable) {
        this.tmptable = tmptable;
    }

    public List<PageColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<PageColumn> columns) {
        this.columns = columns;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getHandwriteWhere() {
        return handwriteWhere;
    }

    public void setHandwriteWhere(String handwriteWhere) {
        this.handwriteWhere = handwriteWhere;
    }

    public String getHandwriteHaving() {
        return handwriteHaving;
    }

    public void setHandwriteHaving(String handwriteHaving) {
        this.handwriteHaving = handwriteHaving;
    }

    public List<Sfiltration> getFilters() {
        return filters;
    }

    public void setFilters(List<Sfiltration> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "ChartRule{" +
                "datasetId='" + datasetId + '\'' +
                ", tmptable='" + tmptable + '\'' +
                ", dbType='" + dbType + '\'' +
                ", columns=" + columns +
                ", filters=" + filters +
                ", handwriteWhere='" + handwriteWhere + '\'' +
                ", handwriteHaving='" + handwriteHaving + '\'' +
                '}';
    }
}
