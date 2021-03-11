package alex.learn.common.chartinfo;

import alex.learn.common.stmt.exceptions.IlegalValueException;
import alex.learn.common.stmt.exceptions.OperationOderException;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/7/18
 * 废弃，产品反馈日期字段只适配date类型，字符串类型不需要再考虑
 */
public class Filtration implements Serializable {
    private String columnname;
    private String columntype;
    private String max;
    private String min;
    private String dformat;

    private List<String> legals = new ArrayList<>();
    private Map<String, String> regxx = new HashMap<>();

    public Filtration() {
        this.legals.add("NUMBER");
        this.legals.add("FLOAT");
        this.legals.add("LONG");
        this.legals.add("INTEGER");
        this.legals.add("DATE");
        this.regxx.put("yyyy-mm-dd", "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
        this.regxx.put("yyyy-mm-dd hh:mm:ss", "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$");
        this.regxx.put("yyyymmdd", "^[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$");
        this.regxx.put("yyyymmdd hh:mm:ss", "^[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$");
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

    public void setColumntype(String columntype) throws  IlegalValueException {
        for (String legal : this.legals) {
            if (null != columntype && columntype.equalsIgnoreCase(legal)) {
                this.columntype = columntype.toUpperCase();
            }
        }
        if (null == this.columntype) {
            throw new IlegalValueException("Illegal value is found.");
        }
    }


    public String getMax() {
        return max;
    }

    public void setMax(String max) throws  ParseException, IlegalValueException, OperationOderException {
        long time = 0L;
        SimpleDateFormat format = null;
        if (null == this.columntype) {
            throw new OperationOderException("Columntype must be set prior.");
        } else if ("DATE".equals(this.columntype)) {
            if (max.matches(this.regxx.get("yyyy-mm-dd"))) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                time = format.parse(max).getTime();
                this.dformat="yyyy-mm-dd";
            }else if(max.matches(this.regxx.get("yyyymmdd"))) {
                format = new SimpleDateFormat("yyyyMMdd");
                time = format.parse(max).getTime();
                this.dformat="yyyymmdd";
            }else if(max.matches(this.regxx.get("yyyy-mm-dd hh:mm:ss"))) {
                format = new SimpleDateFormat("yyyy-mm-dd hh:mm:s");
                time = format.parse(max).getTime();
                this.dformat="yyyy-mm-dd hh:mm:ss";
            }else if(max.matches(this.regxx.get("yyyymmdd hh:mm:ss"))) {
                format = new SimpleDateFormat("yyyymmdd hh:mm:s");
                time = format.parse(max).getTime();
                this.dformat="yyyymmdd hh:mm:ss";
            } else {
                throw new IlegalValueException("Date value has wrong format.");
            }
            this.max=time+"";
        } else {
            if (isNumeric(max)) {
                this.max = max;
            } else {
                throw new IlegalValueException("Illegal value is found.");
            }
        }
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) throws IlegalValueException, OperationOderException, ParseException {
        long time = 0L;
        SimpleDateFormat format = null;
        if (null == this.columntype) {
            throw new OperationOderException("Columntype must be set prior.");
        } else if ("DATE".equals(this.columntype)) {
            if (min.matches(this.regxx.get("yyyy-mm-dd"))) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                time = format.parse(min).getTime();
            }else if(min.matches(this.regxx.get("yyyymmdd"))) {
                format = new SimpleDateFormat("yyyyMMdd");
                time = format.parse(min).getTime();
            }else if(min.matches(this.regxx.get("yyyy-mm-dd hh:mm:ss"))) {
                format = new SimpleDateFormat("yyyy-mm-dd hh:mm:s");
                time = format.parse(min).getTime();
            }else if(min.matches(this.regxx.get("yyyymmdd hh:mm:ss"))) {
                format = new SimpleDateFormat("yyyymmdd hh:mm:s");
                time = format.parse(min).getTime();
            } else {
                throw new IlegalValueException("Date value has wrong format.");
            }
            this.min=time+"";
        } else {
            if (isNumeric(min)) {
                this.min = min;
            } else {
                throw new IlegalValueException("Illegal value is found.");
            }
        }
    }
    public String getDformat() {
        return dformat;
    }

    public void setDformat(String dformat) {
        this.dformat = dformat;
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
        return "Filtration{" + "columnname='" + columnname + '\'' + ", columntype='" + columntype + '\'' + ", max='" + max + '\''
                + ", min='" + min + '\'' + ", legals=" + legals + '}';
    }
}