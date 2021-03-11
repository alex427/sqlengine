package alex.learn.sqlexecute.interfaces;

import alex.learn.common.stmt.beans.ConnectionInfo;
import alex.learn.common.stmt.beans.GeneralComponets;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/7/5
 */
public interface ClientUtilsInterface {

    public ConnectionInfo getLocalConnectionInfo();

    //创建数据库连接
     Connection getConnection(ConnectionInfo info);

    //创建临时表数据库连接
     Connection getTmpConnection();

    //fullurl
     String getFullUrl(ConnectionInfo info);

    //向临时表写入数据, 写入成功返回表名
    //以下包含两个db操作，两者具备原子性
     String wrapAndInsert(List<String> columns, List<List<String>> values, int sep, String tmptable, String datasetId);

    //执行查询语句
     ResultSet executeQuery(Connection conn, String sql) throws Exception;

     void closeConnection(Connection conn);

    // 获取数据库中的表名称与视图名称
    //本例只提供表名信息，如果需要其它元数据信息，自行扩展
    public List<String> getTables(Connection conn, ConnectionInfo info) throws Exception;

}
