package alex.learn.common.stmt.beans;

import alex.learn.common.stmt.exceptions.IlegalParamTypeException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * author  : zhiguang
 * date    : 2018/7/13
 */
public class FuncParam implements Serializable {
    private String pvalue;
    private String ptype;
    private List<String> list = new ArrayList<>();

    public FuncParam() {
        this.list.add("int");
        this.list.add("string");
        this.list.add("long");
        this.list.add("double");
        this.list.add("date");
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) throws IlegalParamTypeException {
        if (this.list.contains(ptype)){
            this.ptype = ptype;
        } else {
            throw new IlegalParamTypeException("ptype is required among [ int,string,date,double ]");
        }

    }

    @Override
    public String toString() {
        return "FuncParam{" +
                "pvalue='" + pvalue + '\'' +
                ", ptype='" + ptype + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuncParam funcParam = (FuncParam) o;
        return Objects.equals(pvalue, funcParam.pvalue) && Objects.equals(ptype, funcParam.ptype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pvalue, ptype);
    }
}
