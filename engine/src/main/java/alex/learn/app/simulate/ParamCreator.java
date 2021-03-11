package alex.learn.app.simulate;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

/**
 * author  : zhiguang
 * date    : 2018/6/12
 */
public class ParamCreator {

    public static void main(String[] args) throws Exception {
        ParamHolder holder = createExample();
        Gson gson = new Gson();
        String param = gson.toJson(holder);
        String clausemeta = gson.toJson(holder.getClauseMeta());
        System.out.println(param);
        System.out.println(clausemeta);
    }


    
    public static WhereTree produceTree(){
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


    public static ParamHolder createExample() throws IlegalColumException, IlegalParamTypeException {

        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setDbType("oracle");
        connectionInfo.setDbName("bigdata");
        connectionInfo.setIpAddr("172.16.16.79");
        connectionInfo.setService("ora11gtest");
        connectionInfo.setPwd("123456");
        connectionInfo.setUname("TEST_VISUAL");
        connectionInfo.setPort(1521);

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

        Column column4 = new Column();
        column4.setColumnName("city");
        column4.setTableName("tmpusers");

        Func func1 = new Func();
        func1.setFuncname("SUM");
        func1.setMeasurename("playtime");
        func1.setTalias("ttime");
        func1.setTableName("tmpusers");

        Func func2 = new Func();
        func2.setFuncname("COUNT");
        func2.setMeasurename("name");
        func2.setTalias("tname");
        func2.setTableName("tmpusers");
        FuncParam fp = new FuncParam();
        fp.setPtype("int");
        fp.setPvalue("20");

        FuncParam fp2 = new FuncParam();//只是演示如何组装，但不能这样使用。
        fp2.setPtype("string");
        fp2.setPvalue("ab");
        FuncParam[] arr = {fp,fp2};
        func2.setFuncParams(arr);

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
        column3.setFuncname("CONCAT");
        column3.setFuncParams(arr);
        column4.setFuncname("TRIM");
        column4.setFuncParams(arr);
        selectlist.add(column4);
        selectlist.add(column3);
        clauseMeta.setSelectdims(selectlist);

        List funclist = new ArrayList();
        funclist.add(func1);
        funclist.add(func2);
        clauseMeta.setFunclist(funclist);

        clauseMeta.setMaintable("tmpusers");

        List grouplist = new ArrayList();
        clauseMeta.setGroupdims(selectlist);

        List orderlist = new ArrayList();
        OrderByy order1 = new OrderByy(column1,"desc");
        OrderByy order2 = new OrderByy(column2,"desc");

        orderlist.add(order1);
        orderlist.add(order2);
        clauseMeta.setOrders(orderlist);

        WhereTree tree = produceTree();
        clauseMeta.setWheretree(tree);

        clauseMeta.setLimitamount(10);

        ParamHolder holder = new ParamHolder();
        holder.setHisMaster(new HisMaster("100000001","1101","100001","1001"));
        holder.setClauseMeta(clauseMeta);
        holder.setConnectionInfo(connectionInfo);

        return holder;
    }




}
