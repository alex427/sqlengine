package alex.learn.dataparse.holderadapte.impls;

import com.google.gson.Gson;
import alex.learn.dataparse.holderadapte.interfaces.DataSourceAdaptee;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/12
 * 废弃
 */
public class OracleHolderAdaptee implements DataSourceAdaptee<ClauseMeta> {

    @Override
    public MetadataHolder convert(ClauseMeta clauseMeta) throws IlegalColumException {
        Gson gson = new Gson();
        System.out.println("clauseMeta:      "+gson.toJson(clauseMeta));

        //1. 替换where 条件中的操作符号
        WhereTree tree = clauseMeta.getWheretree();
        if (null != tree.getRoot()) {
            inOrderTraverse(tree.getRoot());
        } else {
            //支持无where条件
            //throw new IlegalColumException(" whereclause is missed. ");
        }

        //2. 检查
        correctGroupBy(clauseMeta);
        correctOrderBy(clauseMeta);

        //3.转换
        MetadataHolder mh = createMetaHolder(clauseMeta);
        System.out.println("metaholder:      "+gson.toJson(mh));
        return mh;
    }


    //中序遍历: 修改值
    public void inOrderTraverse(WhereNode root) throws IlegalColumException {

        if (null != root) {

            inOrderTraverse(root.getLeftChild());
            inOrderTraverse(root.getRightChild());

            if (null !=  root.getClauses()) {
                for (WhereClause cluase : root.getClauses()) {
                    //替换操作符
                    String operator = new String(cluase.getOperator());
                    if ("eq".equals(operator) ) {
                        cluase.setOperator("=");
                    } else if ("gt".equals(operator) ) {
                        cluase.setOperator(">");
                    } else if ("lt".equals(operator) ) {
                        cluase.setOperator("<");
                    } else {
                        throw new IlegalColumException(operator+"wrong operator "+ operator +" is found . ");
                    }

                    //替换String类型的值
                    if (null != cluase.getValue()) {
                        if (!cluase.isNumeric(cluase.getValue())) {
                            cluase.setValue("\'" + cluase.getValue() + "\'");
                        }
                    } else {
                        throw new IlegalColumException(" whereclause has no value. ");
                    }
                }
            }
        }
    }

    //规范orderby数据
    public void correctOrderBy(ClauseMeta clauseMeta){
        List<Column> groupdims = clauseMeta.getGroupdims();
        List<OrderByy>  orders = clauseMeta.getOrders();

        for(OrderByy orderByy : orders){
            String a = orderByy.getColumn().getTableName();
            String b = orderByy.getColumn().getColumnName();
            for(Column c: groupdims){
                if(c.getTableName().equals(a) && c.getColumnName().equals(b)){
                    orderByy.setFlag(1);
                }
            }
        }
    }

    //规范grouoby数据
    // select字段必须存在于groupby字段列表中
    public void correctGroupBy(ClauseMeta clauseMeta){
        if (  clauseMeta.getGroupdims().containsAll(clauseMeta.getSelectdims())  ){
            //目前两组元素来自同一个页面输入，没有问题。
        }
    }


    //封装metaholder
    public MetadataHolder createMetaHolder(ClauseMeta cm){
        MetadataHolder mh = new MetadataHolder();
        mh.setDbType(cm.getDbType());
        mh.setSelectdims(cm.getSelectdims());
        mh.setFunclist(cm.getFunclist());
        mh.setMaintable(cm.getMaintable());
        mh.setJoinlist(cm.getJoinlist());
        mh.setWheretree(cm.getWheretree());
        mh.setGroupdims(cm.getGroupdims());
        mh.setOrders(cm.getOrders());
        mh.setLimitamount(cm.getLimitamount());
        return mh;
    }

}
