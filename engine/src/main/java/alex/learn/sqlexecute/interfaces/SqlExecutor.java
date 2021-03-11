package alex.learn.sqlexecute.interfaces;

import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 * TODO: 优化，目前暂时用一个ClientUtil
 */
public abstract class SqlExecutor {

    //获取sql, 一个chart可能对应多个sql, 所以返回一个list{sql...}
    public abstract List<String> getTargetSql(String chartId);

    //执行sql
    public abstract void executeSql(List<String> sqllist);

    //存储到结果集
    public abstract void saveResult();

    //返回到前端
    //与存储结果集异步执行
    public abstract void feedResult();
}
