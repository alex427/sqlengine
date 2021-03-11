package alex.learn.common.chartinfo;

import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/7/6
 */
public class PageColumn implements Serializable {

    private String columnname;
    private String columntype;
    private String mord;

    public PageColumn() {
    }

    public PageColumn(String columnname, String columntype, String mord) {
        this.columnname = columnname;
        this.columntype = columntype;
        this.mord = mord;
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

    public void setColumntype(String columntype) {
        this.columntype = columntype;
    }

    public String getMord() {
        return mord;
    }

    public void setMord(String mord) {
        this.mord = mord;
    }

    @Override
    public String toString() {
        return "PageColumn{" +
                "columnname='" + columnname + '\'' +
                ", columntype='" + columntype + '\'' +
                ", mord='" + mord + '\'' +
                '}';
    }
}
