package alex.learn.dataparse.parsers;

import com.google.gson.Gson;
import alex.learn.common.chartinfo.ChartRule;
import alex.learn.common.chartinfo.PageColumn;
import alex.learn.common.chartinfo.Sfiltration;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.IlegalColumException;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/7/2
 */
public abstract class OracleParser {

    public abstract ClauseMeta makeclause(String msg) throws IlegalColumException;

    public abstract ClauseMeta makeclause(ChartRule chartRule) throws IlegalColumException;


    public MetadataHolder convert(ClauseMeta clauseMeta) throws IlegalColumException {
        Gson gson = new Gson();
        System.out.println("before convert clauseMeta:      " + gson.toJson(clauseMeta));

        //1. 替换where 条件中的操作符号
        if (null != clauseMeta.getWheretree()) {
            WhereTree tree = clauseMeta.getWheretree();
            if (null != tree.getRoot()) {
                inOrderTraverse(tree.getRoot());
            } else {
                //支持无where条件
                //throw new IlegalColumException(" whereclause is missed. ");
            }
        }

        //2. 检查
        correctGroupBy(clauseMeta);
        correctOrderBy(clauseMeta);

        //3.转换
        MetadataHolder mh = createMetaHolder(clauseMeta);
        System.out.println("after convert metaholder:      " + gson.toJson(mh));
        return mh;
    }


    //中序遍历: 修改值
    public void inOrderTraverse(WhereNode root) throws IlegalColumException {
        if (null != root) {
            inOrderTraverse(root.getLeftChild());
            inOrderTraverse(root.getRightChild());

            if (null != root.getClauses()) {
                for (WhereClause cluase : root.getClauses()) {
                    //替换操作符
                    String operator = new String(cluase.getOperator());
                    if ("eq".equals(operator)) {
                        cluase.setOperator("=");
                    } else if ("gt".equals(operator)) {
                        cluase.setOperator(">");
                    } else if ("lt".equals(operator)) {
                        cluase.setOperator("<");
                    } else {
                        throw new IlegalColumException(operator + "wrong operator " + operator + " is found . ");
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
    public void correctOrderBy(ClauseMeta clauseMeta) {
        List<Column> groupdims = clauseMeta.getGroupdims();
        List<OrderByy> orders = clauseMeta.getOrders();
        if (null != groupdims && groupdims.size() > 0) {
            for (OrderByy orderByy : orders) {
                String a = orderByy.getColumn().getTableName();
                String b = orderByy.getColumn().getColumnName();
                for (Column c : groupdims) {
                    //这个校验逻辑已经过时，待初版稳定之后，确定完整逻辑
                    if (c.getTableName().equals(a) && c.getColumnName().equals(b)) {
                        orderByy.setFlag(1);
                    }
                }
            }
        }
    }

    //规范grouoby数据
    // select字段必须存在于groupby字段列表中
    public void correctGroupBy(ClauseMeta clauseMeta) {
        // if (  clauseMeta.getGroupdims().containsAll(clauseMeta.getSelectdims())  ){
        //目前两组元素来自同一个页面输入，没有问题。
        // }
    }


    //封装metaholder
    public MetadataHolder createMetaHolder(ClauseMeta cm) {
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
        mh.setHandwriteWhere(cm.getHandwriteWhere());
        mh.setHandwriteHaving(cm.getHandwriteHaving());
        return mh;
    }

    public List<Column> selectlist(ChartRule chartRule) throws IlegalColumException {
        List<Column> selectlist = new ArrayList<>();
        String tablename = chartRule.getTmptable();
        List<PageColumn> list = chartRule.getColumns();
        for (PageColumn c : list) {
            if (null != c.getMord() && "d".equals(c.getMord())) {
                selectlist.add(new Column(tablename, c.getColumnname()));
            }
        }
        return selectlist;
    }

    public List<Func> funclist(ChartRule chartRule) throws IlegalColumException {
        List<Func> funclist = new ArrayList<>();
        String tablename = chartRule.getTmptable();
        List<PageColumn> list = chartRule.getColumns();
        for (PageColumn c : list) {
            if (null != c.getMord() && "m".equals(c.getMord())) {
                funclist.add(new Func(null, c.getColumnname(), tablename));
            }
        }
        return funclist;
    }

    //支持多个过滤器是AND的关系
    public WhereTree wherelist(ChartRule chartRule) throws IlegalColumException {
        WhereTree tree = new WhereTree();
        List<Sfiltration> filters = chartRule.getFilters();
        if( null!=filters && filters.size()>0 ){
            List<WhereClause> list = new ArrayList<>();
            for (Sfiltration filter: filters){
                if(null != filter){
                    if( null!=filter.getMax() && !"".equalsIgnoreCase(filter.getMax()) ){
                        WhereClause max = new WhereClause();
                        max.setColumnName(filter.getColumnname());
                        max.setOperator("lt");
                        max.setValue(filter.getMax());
                        list.add(max);
                    }
                    if( null!=filter.getMin() && !"".equalsIgnoreCase(filter.getMin()) ){
                        WhereClause min = new WhereClause();
                        min.setColumnName(filter.getColumnname());
                        min.setOperator("gt");
                        min.setValue(filter.getMin());
                        list.add(min);
                    }
                }
            }
            WhereNode node1 = new WhereNode();
            node1.setClauses(list);
            node1.setLeftChild(null);
            node1.setRightChild(null);
            WhereNode root = new WhereNode();
            root.setLeftChild(node1);
            tree.setRoot(root);
        }
        return tree;
    }


    public List<OrderByy> orderbylist(ChartRule chartRule) throws IlegalColumException {
        List<OrderByy> orderlist = new ArrayList<>();
        String tablename = chartRule.getTmptable();
        List<PageColumn> list = chartRule.getColumns();
        for (PageColumn c : list) {
            Column colum = new Column(tablename, c.getColumnname());
            orderlist.add(new OrderByy(colum, "desc"));
        }
        return orderlist;
    }

    public String makeWhere(String msg) {
        if (null != msg && "".equalsIgnoreCase(msg)) {
            msg = standardize(msg);
        }
        return msg;
    }

    public String makeHaving(String msg) {
        if (null != msg && "".equalsIgnoreCase(msg)) {
            msg = standardize(msg);
        }
        return msg;
    }

    public String standardize(String msg) {
        //空格处理
        msg = msg.replace("  ", " ");
        msg = msg.replace(" =", "=");
        msg = msg.replace("= ", "=");
        return msg;
    }


    public WhereTree produceTree(ChartRule chartRule) {

        return null;
    }
}
