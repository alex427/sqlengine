package alex.learn.sqlgenerate.interfaces;


/**
 * author  : zhiguang
 * date    : 2018/6/7
 * 抽象父类
 */
public abstract class SqlGenerator<T> {

    Buildable<T> buildable;

    public SqlGenerator(Buildable buildable) {
        this.buildable = buildable;
    }

    //生成sql
    public String generateSql(T t) throws Exception {
        return this.buildable.generateSql(t);
    }

}
