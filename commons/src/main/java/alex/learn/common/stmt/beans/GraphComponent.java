package alex.learn.common.stmt.beans;

import java.io.Serializable;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 * 待启用
 */
public class GraphComponent  implements Serializable {
    private String cid;
    private String cname;
    private String code;
    private List<String> jscharts;

    public GraphComponent() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getJscharts() {
        return jscharts;
    }

    public void setJscharts(List<String> jscharts) {
        this.jscharts = jscharts;
    }
}
