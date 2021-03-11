package alex.learn.common.stmt.beans;


import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 */
public class WhereClause  implements Serializable {

    private String columnName;
    private String tablename;
    private String operator;
    private String value;

    public WhereClause() {

    }

    public WhereClause(String columnName, String tablename, String operator, String value) {
        this.columnName = columnName;
        this.tablename = tablename;
        this.operator = operator;
        this.value = value;
    }

    //verify逻辑，不太好写

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    //判断值类型 true表示属于数值类型
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue2(String value) {
        if (isNumeric(value)) {
            this.value = value;
        } else {
            this.value = "\'" + value + "\'";
        }
    }


}
