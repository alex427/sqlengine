package alex.learn.common.stmt.beans;

import java.io.Serializable;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/21
 */
public class ClauseMeta  implements Serializable {

    private String dbType;
    private String dbName;
    private String maintable;         //主表
    private List<Join> joinlist;      //关联表集合
    private List<Func> funclist;      //度量字段集合
    private WhereTree wheretree;      //where条件
    private List<Column> selectdims; //维度字段集合
    private List<Column> groupdims;  //维度字段集合，暂时约定与selectdims一样
    private List<OrderByy> orders;   //排序语句
    private int limitamount;
    //private int limitamount = 20;   //limit 默认20条
    private String handwriteWhere;
    private String handwriteHaving;

    public ClauseMeta() {
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getMaintable() {
        return maintable;
    }

    public void setMaintable(String maintable) {
        this.maintable = maintable;
    }

    public List<Join> getJoinlist() {
        return joinlist;
    }

    public void setJoinlist(List<Join> joinlist) {
        this.joinlist = joinlist;
    }

    public List<Func> getFunclist() {
        return funclist;
    }

    public void setFunclist(List<Func> funclist) {
        this.funclist = funclist;
    }

    public WhereTree getWheretree() {
        return wheretree;
    }

    public void setWheretree(WhereTree wheretree) {
        this.wheretree = wheretree;
    }

    public List<Column> getSelectdims() {
        return selectdims;
    }

    public void setSelectdims(List<Column> selectdims) {
        this.selectdims = selectdims;
    }

    public List<Column> getGroupdims() {
        return groupdims;
    }

    public void setGroupdims(List<Column> groupdims) {
        this.groupdims = groupdims;
    }

    public List<OrderByy> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderByy> orders) {
        this.orders = orders;
    }

    public int getLimitamount() {
        return limitamount;
    }

    public void setLimitamount(int limitamount) {
        this.limitamount = limitamount;
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
}
