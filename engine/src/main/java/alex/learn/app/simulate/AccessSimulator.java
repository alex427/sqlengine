package alex.learn.app.simulate;

import com.google.gson.Gson;
import alex.learn.sqlgenerate.builders.OracleSqlBuilder;
import alex.learn.sqlgenerate.generators.FirstGenerator;
import alex.learn.sqlgenerate.interfaces.Buildable;
import alex.learn.common.stmt.beans.*;
import alex.learn.common.stmt.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : zhiguang
 * date    : 2018/6/7
 */
public class AccessSimulator {

    public static void main(String[] args) throws Exception {

        MetadataHolder holder = exampleCheck();
        String jsonmsg = prepareJson(holder);
        System.out.println(jsonmsg);

        Gson gson = new Gson();
        MetadataHolder holder_ = (MetadataHolder) gson.fromJson(jsonmsg, MetadataHolder.class);

        Buildable builder = new OracleSqlBuilder();
        FirstGenerator gen = new FirstGenerator(builder);
        gen.remind();
        gen.generateSql(holder_);

    }


    public static String prepareJson(MetadataHolder holder) {
        Gson gson = new Gson();
        return gson.toJson(holder);
    }

    public static WhereTree produceTree() {
        //封装where条件到一棵树中
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


    public static MetadataHolder exampleCheck() throws IlegalColumException {
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
        func1.setTableName("tmpusers");

        Func func2 = new Func();
        func2.setFuncname("count");
        func2.setMeasurename("name");
        func2.setTableName("tmpusers");

        Join join = new Join();
        join.setJoinColumns(null);
        join.setJoinTable(null);

        MetadataHolder holder = new MetadataHolder();
        holder.setDbType("oracle");

        List selectlist = new ArrayList();
        selectlist.add(column1);
        selectlist.add(column2);
        //selectlist.add(column3);
        holder.setSelectdims(selectlist);

        List funclist = new ArrayList();
        funclist.add(func1);
        funclist.add(func2);
        holder.setFunclist(funclist);

        holder.setMaintable("tmpusers");

        List grouplist = new ArrayList();
        grouplist.add(column1);
        grouplist.add(column2);
        //grouplist.add(column3);
        holder.setGroupdims(grouplist);

        holder.setLimitamount(10);

        List orderlist = new ArrayList();
        OrderByy order1 = new OrderByy(column1, "desc");
        order1.setFlag(1);
        OrderByy order2 = new OrderByy(column2, "desc");
        order1.setFlag(2);
        OrderByy order3 = new OrderByy(column3, "desc");
        orderlist.add(order1);
        orderlist.add(order2);
        orderlist.add(order3);
        holder.setOrders(orderlist);

        WhereTree tree = produceTree();
        holder.setWheretree(tree);

        return holder;
    }

}
