package alex.learn.common.stmt.beans;


import alex.learn.common.stmt.exceptions.IlegalColumException;

import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 */
public class Column implements Serializable {

    private String tableName;
    private String columnName;
    private String columnType;
    private String funcname;
    private String talias;
    private FuncParam[] funcParams;//索引与参数位置对应

    public Column() {
    }

    //这里没加*号校验
    public Column(String tableName, String columnName) {
        if (null != tableName) {
            this.tableName = tableName;
        }
        this.columnName = columnName;
        this.columnType = "string";//默认字符串类型
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        if (null != tableName) {
            this.tableName = tableName.toLowerCase();
            this.columnType = "string";//默认字符串类型
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) throws IlegalColumException {
        if ("*".equals(columnName)) {
            throw new IlegalColumException(" SELECT *  is not allowed. Potential risk exists.");
        }
        this.columnName = columnName.toLowerCase();
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFuncname() {
        return funcname;
    }

    public void setFuncname(String funcname) throws IlegalColumException {
        if (null == this.columnName) {
            throw new IlegalColumException("ColumnName must be set earlier than funcName.");
        } else if ("".equalsIgnoreCase(this.columnName)) {
            throw new IlegalColumException("Illegal coloumn is found.");
        } else {
            if (null != funcname) {
                this.funcname = funcname.toUpperCase();
                this.talias = funcname.toLowerCase() + "_" + this.columnName;
            }

        }
    }

    public String getTalias() {
        return talias;
    }

    public void setTalias(String talias) {
        this.talias = talias;
    }

    public FuncParam[] getFuncParams() {
        return funcParams;
    }

    public void setFuncParams(FuncParam[] funcParams) {
        this.funcParams = funcParams;
    }
}
