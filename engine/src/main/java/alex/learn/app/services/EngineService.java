package alex.learn.app.services;


import alex.learn.common.chartinfo.ChartRule;
import alex.learn.common.stmt.beans.ClauseMeta;
import alex.learn.common.stmt.beans.ConnectionInfo;

import java.sql.ResultSet;

/**
 * author  : zhiguang
 * date    : 2018/7/2
 */
public interface EngineService {

    //获取条件语句
    //临时用来适应当前产品不确定的情况，实际没有存在必要
    ClauseMeta getClause(String chartrule);
    ClauseMeta getClause(ChartRule pageRule);

    //解析服务
    String parse(ClauseMeta clauseMeta);

    //语句执行服务
    ResultSet executeStatement(ConnectionInfo info, String sql);

    //本地临时表查询
    ResultSet executeStatement(String localtable, String sql);

    //结果集包装服务
    String wrapResult(ResultSet resultSet, ClauseMeta clauseMeta, String charcode);

    //简易包装
    String wrapResult(ResultSet resultSet);

    //临时表写入
    String[] wrapAndInsert(ConnectionInfo conninfo, ClauseMeta clauseMeta, String tablename, String datasetId);

    //数据预览
    String preview(ConnectionInfo conninfo, ClauseMeta clauseMeta);

    //结果集包装--单独支持数据预览
    String wrapResultForPreview(ResultSet resultSet, ClauseMeta clauseMeta);


}
