package alex.learn.common.wrapbeans;

import java.util.List;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/6/22
 */
public class DataEntity {

    private  List<String> columns;
    private  List<Map<String,String>> rows;

    public DataEntity() {
    }

    public DataEntity(List<String> columns, List<Map<String, String>> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }
}
