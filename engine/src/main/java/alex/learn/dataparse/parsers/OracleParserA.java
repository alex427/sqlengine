package alex.learn.dataparse.parsers;

import alex.learn.common.chartinfo.ChartRule;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.IlegalColumException;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/7/2
 */
public class OracleParserA extends OracleParser {


    @Override
    public ClauseMeta makeclause(ChartRule chartRule) throws IlegalColumException {
        ClauseMeta clauseMeta = new ClauseMeta();
        clauseMeta.setDbType(chartRule.getDbType());
        clauseMeta.setMaintable(chartRule.getTmptable());
        List<Column> sl = selectlist(chartRule);
        clauseMeta.setSelectdims(sl);

        List<Func> fl = funclist(chartRule);
        if (verifyFunc(fl)) {
            clauseMeta.setGroupdims(sl);
        }

        clauseMeta.setFunclist(fl);

        WhereTree tree = wherelist(chartRule);
        if(null != tree && null != tree.getRoot()){
            clauseMeta.setWheretree(tree);
        }
        clauseMeta.setOrders(orderbylist(chartRule));
        clauseMeta.setLimitamount(10000);//无更好策略，暂定默认为10000
        clauseMeta.setHandwriteWhere(makeWhere(chartRule.getHandwriteWhere()));
        clauseMeta.setHandwriteHaving(makeHaving(chartRule.getHandwriteHaving()));
        return clauseMeta;
    }

    public boolean verifyFunc(List<Func> list) {
        boolean flag = false;
        if (null != list && list.size() > 0) {
            for (Func f : list) {
                if (null == f.getFuncname() || "".equals(f.getFuncname())) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /*****************************************************************/
    //以下用来测试
    public ClauseMeta createExample() throws IlegalColumException {

        ClauseMeta clauseMeta = new ClauseMeta();

        Column column1 = new Column();
        column1.setColumnName("province");
        column1.setTableName("tmpusers");

        Column column2 = new Column();
        column2.setColumnName("gender");
        column2.setTableName("tmpusers");

        Column column3 = new Column();
        column3.setColumnName("job");
        column3.setTableName("tmpusers");

        Func func1 = new Func();
        func1.setFuncname("sum");
        func1.setMeasurename("playtime");
        func1.setTalias("ttime");
        func1.setTableName("tmpusers");

        Func func2 = new Func();
        func2.setFuncname("count");
        func2.setMeasurename("name");
        func2.setTalias("tname");
        func2.setTableName("tmpusers");

        Join join1 = new Join();
        List<String> joincolumns = new ArrayList();
        joincolumns.add("id");
        joincolumns.add("name");
        join1.setJoinColumns(joincolumns);
        join1.setJoinTable("tmpmoney");
        join1.setMainTable("tmpusers");
        join1.setJointype(" LEFT JOIN ");

        Join join2 = new Join();
        List<String> joincolumns2 = new ArrayList();
        joincolumns2.add("id");
        joincolumns2.add("name");
        join2.setJoinColumns(joincolumns2);
        join2.setJoinTable("tmplength");
        join2.setMainTable("tmpusers");
        join2.setJointype(" LEFT JOIN ");

        List joinlist = new ArrayList();
        joinlist.add(join1);
        joinlist.add(join2);
        clauseMeta.setJoinlist(joinlist);

        List selectlist = new ArrayList();
        selectlist.add(column1);
        selectlist.add(column2);
        //selectlist.add(column3);
        clauseMeta.setSelectdims(selectlist);

        List funclist = new ArrayList();
        funclist.add(func1);
        funclist.add(func2);
        clauseMeta.setFunclist(funclist);

        clauseMeta.setMaintable("tmpusers");

        List grouplist = new ArrayList();
        grouplist.add(column1);
        grouplist.add(column2);
        //grouplist.add(column3);
        clauseMeta.setGroupdims(grouplist);

        List orderlist = new ArrayList();
        OrderByy order1 = new OrderByy(column1, "desc");
        OrderByy order2 = new OrderByy(column2, "desc");

        orderlist.add(order1);
        orderlist.add(order2);
        clauseMeta.setOrders(orderlist);

        WhereTree tree = produceTree();
        clauseMeta.setWheretree(tree);

        clauseMeta.setLimitamount(10);

        return clauseMeta;
    }

    public WhereTree produceTree() {
        List<WhereClause> clauses1 = new ArrayList<>();
        WhereClause clause1 = new WhereClause();
        clause1.setTablename("tmpusers");
        clause1.setColumnName("age");
        clause1.setOperator("gt");
        clause1.setValue("20");

        WhereClause clause2 = new WhereClause();
        clause2.setTablename("tmpusers");
        clause2.setColumnName("gender");
        clause2.setOperator("eq");
        clause2.setValue("male");

        clauses1.add(clause1);
        clauses1.add(clause2);

        List<WhereClause> clauses2 = new ArrayList<>();
        WhereClause clause3 = new WhereClause();
        clause3.setTablename("tmpusers");
        clause3.setColumnName("job");
        clause3.setOperator("eq");
        clause3.setValue("boss");

        clauses2.add(clause3);

        WhereNode node1 = new WhereNode();
        node1.setClauses(clauses1);
        node1.setLeftChild(null);
        node1.setRightChild(null);

        WhereNode node2 = new WhereNode();
        node2.setClauses(clauses2);
        node2.setLeftChild(null);
        node2.setRightChild(null);

        WhereNode root = new WhereNode();
        root.setLeftChild(node1);
        root.setRightChild(node2);

        WhereTree tree = new WhereTree(root);
        return tree;
    }


    @Override
    public ClauseMeta makeclause(String msg) throws IlegalColumException {
        //解析msg， 返回clause
        //测试用
        return createExample();
    }

}
