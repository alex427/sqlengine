package alex.learn.common.stmt.beans;


/**
 * author  : zhiguang
 * date    : 2018/6/7
 */
import java.io.Serializable;
import java.util.List;

public class Join  implements Serializable {

    private String joinTable;
    private String  mainTable;
    private List<String> joinColumns;
    private String jointype;

    public Join() {
    }

    public boolean verify(){
        if( null == this.getJoinTable() || null == this.getJoinColumns() || 0 == this.getJoinColumns().size() || null==this.getJointype() ){
            return false;
        } else {
            return true;
        }
    }


    public String getJointype() {
        return jointype;
    }

    public void setJointype(String jointype) {
        this.jointype = jointype;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public String getMainTable() {
        return mainTable;
    }

    public void setMainTable(String mainTable) {
        this.mainTable = mainTable;
    }

    public List<String> getJoinColumns() {
        return joinColumns;
    }

    public void setJoinColumns(List<String> joinColumns) {
        this.joinColumns = joinColumns;
    }

}
