package alex.learn.common.stmt.beans;


import java.io.Serializable;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 * 用于请求参数的解析
 */

public class ParamHolder  implements Serializable {

    private HisMaster hisMaster;
    private ConnectionInfo connectionInfo;
    private ClauseMeta clauseMeta;


    public ParamHolder() {
    }

    public HisMaster getHisMaster() {
        return hisMaster;
    }

    public void setHisMaster(HisMaster hisMaster) {
        this.hisMaster = hisMaster;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public ClauseMeta getClauseMeta() {
        return clauseMeta;
    }

    public void setClauseMeta(ClauseMeta clauseMeta) {
        this.clauseMeta = clauseMeta;
    }

}
