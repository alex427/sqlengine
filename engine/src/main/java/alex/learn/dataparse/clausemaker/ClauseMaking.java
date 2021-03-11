package alex.learn.dataparse.clausemaker;

import alex.learn.common.chartinfo.ChartRule;
import alex.learn.common.stmt.beans.ClauseMeta;
import alex.learn.common.stmt.exceptions.IlegalColumException;

/**
 * author  : zhiguang
 * date    : 2018/7/2
 * 废弃
 */
public interface ClauseMaking {

    ClauseMeta makeclause(String msg) throws IlegalColumException;

    ClauseMeta makeclause(ChartRule pageRule) throws IlegalColumException;
}
