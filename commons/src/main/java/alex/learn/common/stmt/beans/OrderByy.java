package alex.learn.common.stmt.beans;

import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/6/8
 */
public class OrderByy  implements Serializable {

    private Column column;
    private String directon;
    private int flag; //0 非法， 1合法


    public OrderByy() {
    }

    public OrderByy(Column column, String directon) {
        this.column = column;
        this.directon = directon;
    }


    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getDirecton() {
        return directon;
    }

    public void setDirecton(String directon) {
        this.directon = directon;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}
