package alex.learn.datawrap;

import alex.learn.common.wrapbeans.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/20
 * 顶级包装类
 * 抽象类
 * 不变：向前端返回的数据结构chart
 * 变化：数据resultset结构，参数结构
 */
public abstract class ResultWrapper {

    Chart chart;

    public abstract Chart wrap() throws Exception;

}
