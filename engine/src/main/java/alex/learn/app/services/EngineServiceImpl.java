package alex.learn.app.services;

import com.google.gson.Gson;
import alex.learn.app.simulate.Father;
import alex.learn.common.chartinfo.ChartRule;
import alex.learn.dataparse.parsers.OracleParserA;
import alex.learn.dataparse.parsers.OracleParser;
import alex.learn.datawrap.*;
import alex.learn.common.wrapbeans.Chart;
import alex.learn.common.wrapbeans.DataEntity;
import alex.learn.sqlexecute.utils.DataBaseClient;
import alex.learn.sqlgenerate.builders.OracleSqlBuilder;
import alex.learn.sqlgenerate.generators.FirstGenerator;
import alex.learn.sqlgenerate.interfaces.Buildable;
import org.springframework.stereotype.Service;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

import java.sql.*;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/6/11
 * 4种服务
 * //@Scope("prototype")
 */

@Service
public class EngineServiceImpl implements EngineService {

    private OracleParser parser = new OracleParserA();

    private DataBaseClient dataBaseClient = new DataBaseClient();

    //获取条件语句
    @Override
    public ClauseMeta getClause(ChartRule chartRule) {
        try {
            return this.parser.makeclause(chartRule);
        } catch (IlegalColumException e) {
            e.printStackTrace();
        }
        return null;
    }


    //解析服务
    @Override
    public String parse(ClauseMeta clauseMeta) {
        Buildable builder = new OracleSqlBuilder();
        FirstGenerator generator = new FirstGenerator(builder);
        generator.remind();
        String sql = null;
        try {
             sql = generator.generateSql(this.parser.convert(clauseMeta));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }


    //语句执行服务
    @Override
    public ResultSet executeStatement(ConnectionInfo info, String sql){
        Connection connection = dataBaseClient.getConnection(info);
        ResultSet resultSet = null;
        try {
            resultSet = dataBaseClient.executeQuery(connection,sql);
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet executeStatement(String localtable, String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = dataBaseClient.executeLocalQuery(sql);
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //简易包装
    @Override
    public String wrapResult(ResultSet resultSet) {
        ResultWrapper wrapper = new EasyConcreteWrapper(resultSet);
        try {
            Chart chart = wrapper.wrap();
            Gson gson = new Gson();
            String msg = "{\"easy\":"+gson.toJson(chart.getDataEntity())+"}";
            System.out.println("wrap msg:  "+msg);
            dataBaseClient.closedbconnection();
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //结果集包装服务
    @Override
    public String wrapResult(ResultSet resultSet, ClauseMeta clauseMeta, String charcode){
        ResultWrapper wrapper = new FirstBaseConcreteWrapper(resultSet,clauseMeta,charcode);
        try {
            Chart chart = wrapper.wrap();
            Gson gson = new Gson();
            String msg = "{\""+charcode +"\":"+gson.toJson(chart.getDataEntity())+"}";
            System.out.println("wrap msg:  "+msg);
            dataBaseClient.closedbconnection();
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //临时表写入
    //返回临时表表名，说明写入成功;返回null，说明写入失败
    @Override
    public String[] wrapAndInsert(ConnectionInfo conninfo, ClauseMeta clauseMeta, String tablename, String datasetId) {
        String[] arr = new String[3];
        String sql = parse(clauseMeta);
        ResultSet resultSet = executeStatement(conninfo, sql);
        ResultWrapper wrapper = new ThirdConcreteWrapper(resultSet,clauseMeta,tablename,datasetId);
        try {
            wrapper.wrap();
            arr[0]=datasetId;
            arr[1]=tablename;
            arr[2]=sql;
            dataBaseClient.closedbconnection();
            return arr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //数据预览
    @Override
    public String preview(ConnectionInfo conninfo,  ClauseMeta clauseMeta) {
        //ClauseMeta clauseMeta = getClause( rule);
        String sql = parse(clauseMeta);
        ResultSet resultSet = executeStatement(conninfo, sql);
        return wrapResultForPreview(resultSet,clauseMeta);
    }


    //结果集包装--单独支持数据预览
    public String wrapResultForPreview(ResultSet resultSet, ClauseMeta clauseMeta){
        ResultWrapper wrapper = new SecondConcreteWrapper(resultSet,clauseMeta);
        try {
            Chart chart = wrapper.wrap();
            DataEntity dataEntity =chart.getDataEntity();
            Gson gson = new Gson();
            String msg = "{\""+"preview" +"\":"+gson.toJson(chart.getDataEntity())+"}";
            dataBaseClient.closedbconnection();
            System.out.println("wrap msg:  "+msg);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //获取条件语句
    //临时用来适应当前产品不确定的情况，实际没有存在必要
    @Override
    public ClauseMeta getClause(String chartrule) {
        try {
            return this.parser.makeclause(chartrule);
        } catch (IlegalColumException e) {
            e.printStackTrace();
        }
        return null;
    }


    //测试
    public String test(Father father){
        return father.getName() +"  "+ father.getSon();
    }

}
