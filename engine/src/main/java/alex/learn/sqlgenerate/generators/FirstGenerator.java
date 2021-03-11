package alex.learn.sqlgenerate.generators;


import alex.learn.sqlgenerate.interfaces.Buildable;
import alex.learn.sqlgenerate.interfaces.SqlGenerator;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 *  版本升级， 扩展这个子类, 比如SecondGenerator extends SqlGenerator
 */
public class FirstGenerator extends SqlGenerator {

    public FirstGenerator(Buildable buildable) {
        super(buildable);
    }

    public void remind(){
        System.out.println("input  type        : json ");
        System.out.println("target datasource  : oracle ");
    }

}
