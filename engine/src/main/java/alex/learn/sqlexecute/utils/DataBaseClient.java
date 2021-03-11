package alex.learn.sqlexecute.utils;

import alex.learn.common.stmt.exceptions.IlegalParamException;
import javax.sql.DataSource;
import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.IlegalValueException;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 */
public class DataBaseClient {

    private ResourceReader reader;
    private DataSource dataSource;
    //ThreadLocal中放Map,存在内存泄漏的风险，后面不能再往这个map里面添加东西，尤其是复杂对象。
    private ThreadLocal<Map<String,Connection>> threadLocal = new ThreadLocal<>();

    private Map<String, String> regxx = new HashMap<>();

    //日期格式在此扩展，正则表达式必须多次校验
    public DataBaseClient() {
        this.regxx.put("yyyy-mm-dd", "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
        this.regxx.put("yyyy-mm-dd hh:mm:ss", "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$");
        this.regxx.put("yyyymmdd", "^[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$");
        this.regxx.put("yyyymmdd hh:mm:ss", "^[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$");
    }


    //init
    public void init() {
        //PlatformTransactionManager tx = ApplicationContextProvider.getBeanByBeanClass(PlatformTransactionManager.class);
        if (this.dataSource == null) {
            this.dataSource = ApplicationContextProvider.getBeanByBeanClass(DataSource.class);
        }
        if (this.reader == null) {
            this.reader = ApplicationContextProvider.getBeanByBeanClass(ResourceReader.class);
        }
    }


    //读取配置文件获取临时表连接信息信息
    public ConnectionInfo getLocalConnectionInfo() {
        init();
        ConnectionInfo info = new ConnectionInfo();
        info.setDbType(reader.getDbtype());
        info.setIpAddr(reader.getUrl());
        info.setPort(reader.getPort());
        info.setService(reader.getService());
        info.setUname(reader.getUsername());
        info.setPwd(reader.getPassword());
        return info;
    }


    //创建外部数据库连接
    public Connection getConnection(ConnectionInfo info) {
        init();
        ConnectionInfo local = getLocalConnectionInfo();
        Connection conn = null;
        try {
            if (local.equals(info)) {
                conn = this.dataSource.getConnection();
                System.out.println(conn);
                System.out.println("local db, connection FROM datasource is ready.");
            } else {
                conn = DriverManager.getConnection(getFullUrl(info), info.getUname().toUpperCase(), info.getPwd());
                System.out.println(conn);
                System.out.println("src db connection is created.");
            }
            Map<String,Connection> map = new HashMap<>();
            map.put("ext",conn);
            this.threadLocal.set(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    //创建临时表数据库连接
    public Connection getTmpConnection() {
        init();
        Connection conn = null;
        try {
            if (null != this.dataSource) {
                conn = this.dataSource.getConnection();
                System.out.println(conn);
                System.out.println("Connection from datasource is ready.");
            } else {
                ConnectionInfo info = getLocalConnectionInfo();
                conn = DriverManager.getConnection(getFullUrl(info), info.getUname().toUpperCase(), info.getPwd());
                System.out.println(conn);
                System.out.println("Connection is created.");
            }
            Map<String,Connection> map = new HashMap<>();
            map.put("tmp",conn);
            this.threadLocal.set(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    //fullurl
    public String getFullUrl(ConnectionInfo info) {
        String url = null;
        try {
            if ("1".equals(info.getDbType()) || "oracle".equals(info.getDbType())) {
                Class.forName(GeneralComponets.ORACLE_DRIVER);
                url = GeneralComponets.ORACLE_THIN + ":@" + info.getIpAddr() + ":" + info.getPort() + ":" + info.getService();
            } else if ("2".equals(info.getDbType()) || "mysql".equals(info.getDbType())) {
                Class.forName(GeneralComponets.MYSQL_DRIVER);
                url = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    //向临时表写入数据, 写入成功返回表名
    //以下包含两个db操作，两者具备原子性
    //@Transactional
    public String wrapAndInsert(List<String> columns, List<List<String>> values, List<String> typeinfo, int sep, String tmptable, String datasetId) {
        Connection conn = getTmpConnection();
        String sql = getInsertSql(tmptable, columns);
        try {
            PreparedStatement psmt = (PreparedStatement) conn.prepareStatement(sql.toString());
            for (List<String> single : values) {
                for (int i = 0; i <= sep; i++) {
                    if ("string".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setString(i + 1, single.get(i));
                    } else if ("int".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setInt(i + 1, Integer.parseInt(single.get(i)));
                    } else if ("double".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Double.parseDouble(single.get(i)));
                    } else if ("long".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Long.parseLong(single.get(i)));
                    }else if ("float".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Float.parseFloat(single.get(i)));
                    }else if ("date".equalsIgnoreCase(typeinfo.get(i))) {
                        Timestamp sqlDate = prepareDateInfo(single.get(i));
                        psmt.setTimestamp(i + 1, sqlDate);
                    }else {
                        psmt.setString(i + 1, single.get(i));
                    }
                }
                for (int i = sep + 1; i < single.size(); i++) {
                    if ("int".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setInt(i + 1, Integer.parseInt(single.get(i)));
                    } else if ("double".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Double.parseDouble(single.get(i)));
                    } else if ("long".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1,Long.parseLong(single.get(i)));
                    }else if ("float".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Float.parseFloat(single.get(i)));
                    }else {
                        psmt.setInt(i + 1, Integer.parseInt(single.get(i)));
                    }
                }
                psmt.addBatch();
            }
            int[] feedbk = psmt.executeBatch();
            System.out.println("打印为-2，表示批量插入成功：   " + (null == feedbk ? null : feedbk[0]));

            //修改数据集状态为同步完成
            StringBuffer update = new StringBuffer("update BI_DATASET_INFO a set a.synstatus = 1 WHERE a.datasetid = '" + datasetId + "'");
            //boolean f = psmt.execute(update.toString());
            conn.commit();
            return tmptable;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IlegalValueException e) {
            e.printStackTrace();
        } finally {
            closedbconnection();
        }
        return null;
    }


    //调度程序使用的临时表数据插入方法
    //调用之前先校验传入参数，参数不合法，不要调用
    //返回临时表，表示成功；null，表示失败
    public String justInsert(ResultSet rs, String tmptable, List<String> columns, List<String> typeinfo) throws IlegalParamException {
        //校验
        if (null == tmptable || null == columns || null == typeinfo || columns.size() == 0 || typeinfo.size() == 0 || columns.size() != typeinfo.size()) {
            throw new IlegalParamException("Ilegal param is found, please check.");
        }
        Connection conn = getTmpConnection();
        String sql = getInsertSql(tmptable, columns);
        try {
            PreparedStatement psmt = (PreparedStatement) conn.prepareStatement(sql);
            List<List<String>> values = new ArrayList<>();
            //迭代和保存结果集，如结果集过大，存在内存风险
            while (rs.next()) {
                List<String> record = new ArrayList<>();
                for (int i = 1; i <= columns.size(); i++) {
                    record.add(rs.getString(i));
                }
                values.add(record);
            }
            //自行增加类型
            for (List<String> record : values) {
                for (int i = 0; i < columns.size(); i++) {
                    if ("string".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setString(i + 1, record.get(i));
                    } else if ("int".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setInt(i + 1, Integer.parseInt(record.get(i)));
                    } else if ("double".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Double.parseDouble(record.get(i)));
                    } else if ("long".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Long.parseLong(record.get(i)));
                    }else if ("float".equalsIgnoreCase(typeinfo.get(i))) {
                        psmt.setDouble(i + 1, Float.parseFloat(record.get(i)));
                    }else if ("date".equalsIgnoreCase(typeinfo.get(i))) {
                        Timestamp sqlDate = prepareDateInfo(record.get(i));
                        psmt.setTimestamp(i + 1, sqlDate);
                    }else {
                        psmt.setString(i + 1, record.get(i));
                    }
                }
                psmt.addBatch();
            }
            int[] feedbk = psmt.executeBatch();
            System.out.println("调度程序--打印为-2，表示批量插入成功：   " + (null == feedbk ? null : feedbk[0]));
            conn.commit(); //commit时机和回滚设置，需要认真分析，目前先在这里进行
            return tmptable;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IlegalValueException e) {
            e.printStackTrace();
        } finally {
            closedbconnection();
        }
        return null;
    }

    //生成insert语句
    public String getInsertSql(String tmptable, List<String> columns) {
        StringBuffer sql = new StringBuffer("insert into ");
        sql.append(tmptable);
        sql.append(" ( ");
        for (String c : columns) {
            sql.append(c);
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" ) values ( ");
        for (int a = 0; a < columns.size(); a++) {
            sql.append("?" + ",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        System.out.println("写入数据的语句为" + sql.toString());
        return sql.toString();
    }

    //执行查询语句
    public ResultSet executeQuery(Connection conn, String sql) throws Exception {
        ResultSet rs = null;
        if (sql.startsWith(GeneralComponets.SELECT)) {
            try {
                //创建执行SQL的对象
                Statement stmt = conn.createStatement();
                //执行SQL，并获取返回结果
                rs = stmt.executeQuery(sql);
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //不可关闭
                // closeConnection(conn);
            }
        } else {
            throw new Exception("only query sql is accepted, requiring SELECT head");
        }
        return null;
    }

    //本地临时表查询
    public ResultSet executeLocalQuery(String sql) throws Exception {
        ResultSet rs = null;
        Connection conn = null;
        if (sql.startsWith(GeneralComponets.SELECT)) {
            try {
                //创建执行SQL的对象
                conn = getTmpConnection();
                Statement stmt = conn.createStatement();
                //执行SQL，并获取返回结果
                rs = stmt.executeQuery(sql);
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //不可关闭
                // closeConnection(conn);
            }
        } else {
            throw new Exception("only query sql is accepted, requiring SELECT head");
        }
        return null;
    }

    //废弃,置为private：关闭
    private void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (!conn.isClosed()) {
                System.out.println("connection "+ conn +" is released.");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //确保最后释放连接
    public void closedbconnection() {
        Map<String, Connection> map = threadLocal.get();
        try {
            if (null != map.get("tmp") && !map.get("tmp").isClosed()) {
                System.out.println("Ready to close local conn ---- " + map.get("tmp"));
                map.get("tmp").close();
            }
            if (null != map.get("ext") && !map.get("ext").isClosed()) {
                System.out.println("Ready to close src conn ---- " + map.get("ext"));
                map.get("ext").close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Timestamp prepareDateInfo(String value) throws ParseException, IlegalValueException {
        System.out.println("日期;  "+value);
        SimpleDateFormat format;
        java.util.Date utildate = new java.util.Date();
        if (value.matches(this.regxx.get("yyyy-mm-dd"))) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("aaa");
        }else if(value.matches(this.regxx.get("yyyymmdd"))) {
            format = new SimpleDateFormat("yyyyMMdd");
            System.out.println("bbb");
        }else if(value.matches(this.regxx.get("yyyy-mm-dd hh:mm:ss"))) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("ccc");
        }else if(value.matches(this.regxx.get("yyyymmdd hh:mm:ss"))) {
            format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("ddd");
        } else {
            throw new IlegalValueException("Date value has wrong format.");
        }
        utildate=format.parse(value);
        format.setLenient(false);
        Timestamp sqlDate = new Timestamp(utildate.getTime());
        System.out.println(sqlDate.toString());
        return sqlDate;
    }


    // 获取数据库中的表名称与视图名称
    //本例只提供表名信息，如果需要其它元数据信息，自行扩展
    public List<String> getTables(Connection conn, ConnectionInfo info) throws Exception {
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            rs = metaData.getTables(conn.getCatalog(), info.getUname(), null, new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                list.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closedbconnection();
        }
        return list;
    }

    public ThreadLocal<Map<String, Connection>> getThreadLocal() {
        return threadLocal;
    }

    private void setThreadLocal(ThreadLocal<Map<String, Connection>> threadLocal) {
        this.threadLocal = threadLocal;
    }
}
