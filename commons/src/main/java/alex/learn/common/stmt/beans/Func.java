package alex.learn.common.stmt.beans;


import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 */
public class Func  implements Serializable {

    private String funcname;
    private String measurename;
    private String tableName;
    private String talias;
    private String columnType;
    private FuncParam[] funcParams;//索引与参数位置对应

    public Func() {
    }

    public Func(String funcname, String measurename, String tableName) {
        if(null != funcname){
            this.funcname = funcname.toUpperCase();
        }
        if(null != measurename){
            this.measurename = measurename.toLowerCase();
        }
        if(null != tableName){
            this.tableName = tableName.toLowerCase();
        }
        this.columnType = "int";//默认int类型
        if(null==this.talias && null != measurename){
            this.talias = measurename.toLowerCase();//目前这样
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        if(null != tableName) {
            this.tableName = tableName.toLowerCase();
        }
    }

    public String getFuncname() {
        return funcname;
    }

    public void setFuncname(String funcname) {
        if(null != funcname) {
            this.funcname = funcname.toUpperCase();
        }
    }

    public String getMeasurename() {
        return measurename;
    }

    public void setMeasurename(String measurename) {
        if(null != measurename){
            this.measurename = measurename.toLowerCase();
        }
        this.columnType = "int";//默认int类型
        if(null==this.talias && null != measurename){
            this.talias = measurename.toLowerCase();//目前这样
        }
    }

    public String getTalias() {
        return talias;
    }

    public void setTalias(String talias) {
        this.talias = talias;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public FuncParam[] getFuncParams() {
        return funcParams;
    }

    public void setFuncParams(FuncParam[] funcParams) {
        this.funcParams = funcParams;
    }
}
