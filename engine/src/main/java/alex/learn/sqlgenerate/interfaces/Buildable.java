package alex.learn.sqlgenerate.interfaces;


/**
 * author  : zhiguang
 * date    : 2018/6/7
 * 行为接口Buildable
 */
public interface Buildable<T> {

    String generateSql(T t) throws Exception;

    void persistSql() throws Exception;
}
