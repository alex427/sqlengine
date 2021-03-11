package alex.learn.common.stmt.beans;


/**
 * author  : zhiguang
 * date    : 2018/6/7
 */
import java.io.Serializable;
import java.util.List;

public class MetadataHolder  implements Serializable {

    private String dbType;
    private String maintable;
    private List<Join> joinlist;
    private List<Column> selectdims; //不允许 * 号
    private List<Func> funclist;
    private WhereTree wheretree;
    private List<Column> groupdims;
    private List<OrderByy> orders;
    private int limitamount;

    private String handwriteWhere;
    private String handwriteHaving;

    public MetadataHolder() {
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
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

    public List<Column> getSelectdims() {
        return selectdims;
    }

    public void setSelectdims(List<Column> selectdims) {
        this.selectdims = selectdims;
    }

    public List<Func> getFunclist() {
        return funclist;
    }

    public void setFunclist(List<Func> funclist) {
        this.funclist = funclist;
    }

    public List<Column> getGroupdims() {
        return groupdims;
    }

    public void setGroupdims(List<Column> groupdims) {
        this.groupdims = groupdims;
    }

    public int getLimitamount() {
        return limitamount;
    }

    public void setLimitamount(int limitamount) {
        this.limitamount = limitamount;
    }

    public List<OrderByy> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderByy> orders) {
        this.orders = orders;
    }

    public WhereTree getWheretree() {
        return wheretree;
    }

    public void setWheretree(WhereTree wheretree) {
        this.wheretree = wheretree;
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
