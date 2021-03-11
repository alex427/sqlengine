package alex.learn.dataparse.holderadapte.impls;

import alex.learn.dataparse.holderadapte.interfaces.DataSourceAdaptee;
import alex.learn.dataparse.holderadapte.interfaces.HolderAdaptable;
import alex.learn.common.stmt.beans.*;

/**
 * author  : zhiguang
 * date    : 2018/6/12
 * 适配器模式， 后期扩展以扩展Adaptee为主
 * 约定目标格式： MetadataHolder
 * 废弃
 */
public class HolderAdaptor implements HolderAdaptable<ClauseMeta> {

    private DataSourceAdaptee templateAdaptee;

    //也可以在此处扩展Adaptee， 通过添加新的适配接口来实现

    public HolderAdaptor(DataSourceAdaptee templateAdaptee) {
        this.templateAdaptee = templateAdaptee;
    }

    @Override
    public MetadataHolder convert(ClauseMeta clauseMeta) throws Exception {
        return this.templateAdaptee.convert(clauseMeta);
    }


}
