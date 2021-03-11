package alex.learn.dataparse.holderadapte.interfaces;

import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.IlegalColumException;


/**
 * author  : zhiguang
 * date    : 2018/6/12
 * 废弃
 */
public interface DataSourceAdaptee<T> {
    MetadataHolder convert(T t) throws Exception;
}
