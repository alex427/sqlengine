package alex.learn.dataparse.wheremaker;

import alex.learn.common.stmt.beans.WhereTree;
import alex.learn.common.stmt.exceptions.IlegalColumException;

/**
 * author  : zhiguang
 * date    : 2018/7/2
 * 废弃
 */
public interface WhereMaking {

    WhereTree makeWhere(String msg) throws IlegalColumException;

}
