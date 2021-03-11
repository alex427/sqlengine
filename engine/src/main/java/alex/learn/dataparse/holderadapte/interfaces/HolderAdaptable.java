package alex.learn.dataparse.holderadapte.interfaces;

import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

/**
 * author  : zhiguang
 * date    : 2018/6/12
 * 废弃
 */
public interface HolderAdaptable<T> {
    MetadataHolder convert(T t) throws Exception;
}
