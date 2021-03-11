package alex.learn.common.stmt.beans;

import java.io.Serializable;
import java.util.Objects;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 */
public class ConnectionInfo  implements Serializable {
    private String dbName;//如果不存在跨库，可省略
    private String dbType;
    private String ipAddr;
    private int port;
    private String service;
    private String uname;
    private String pwd;

    public ConnectionInfo() {
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

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "dbType='" + dbType + '\'' +
                ", dbName='" + dbName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", service='" + service + '\'' +
                ", uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo info = (ConnectionInfo) o;
        return port == info.port &&
                Objects.equals(dbType, info.dbType) &&
                Objects.equals(dbName, info.dbName) &&
                Objects.equals(ipAddr, info.ipAddr) &&
                Objects.equals(service, info.service) &&
                Objects.equals(uname, info.uname) &&
                Objects.equals(pwd, info.pwd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dbType, dbName, ipAddr, port, service, uname, pwd);
    }
}
