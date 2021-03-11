package alex.learn.common.chartinfo;

import alex.learn.common.stmt.exceptions.IlegalValueException;
import alex.learn.common.stmt.exceptions.OperationOderException;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/7/20
 * 图表过滤器
 * 要求只考虑日期和数值两种类型
 */
public class Sfiltration implements Serializable {
    private String columnname;
    private String columntype;
    private String max;
    private String min;
    //private String dformat; 不再需要

    //@JsonIgnore
    private List<String> legals = new ArrayList<>();

    //构造代码块执行合法数据初始化
    public Sfiltration() {
        this.legals.add("NUMBER");
        this.legals.add("FLOAT");
        this.legals.add("LONG");
        this.legals.add("INTEGER");
        this.legals.add("DATE");
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getColumntype() {
        return columntype;
    }

    public void setColumntype(String columntype) throws IlegalValueException {
        for (String legal : this.legals) {
            if (null != columntype && columntype.equalsIgnoreCase(legal)) {
                this.columntype = columntype.toUpperCase();
            }
        }
        if (null == this.columntype) {
            throw new IlegalValueException("Illegal columntype is found.");
        }
    }


    public String getMax() {
        return max;
    }

    public void setMax(String max) throws ParseException, IlegalValueException, OperationOderException {
        if (null == this.columntype) {
            throw new OperationOderException("Columntype must be set prior.");
        } else if ("DATE".equals(this.columntype)) {
            this.max = Date.parse(max)+"";
        } else if (isNumeric(max)) {
            this.max = max;
        } else {
            throw new IlegalValueException("Illegal value is found.");
        }
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) throws IlegalValueException, OperationOderException, ParseException {
        if (null == this.columntype) {
            throw new OperationOderException("Columntype must be set prior.");
        } else if ("DATE".equals(this.columntype)) {
            this.min = Date.parse(min)+"";
        } else if (isNumeric(max)) {
            this.min = min;
        } else {
            throw new IlegalValueException("Illegal value is found.");
        }
    }


    //判断值类型 true表示属于数值类型
    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sfiltration{" +
                "columnname='" + columnname + '\'' +
                ", columntype='" + columntype + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", legals=" + legals +
                '}';
    }
}