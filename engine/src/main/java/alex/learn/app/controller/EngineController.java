package alex.learn.app.controller;

import com.google.gson.Gson;
import alex.learn.app.services.EngineService;
import alex.learn.common.crossinterface.TestCrossService;
import alex.learn.common.stmt.exceptions.IlegalColumException;
import alex.learn.sqlexecute.utils.DataBaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import alex.learn.common.stmt.beans.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author  : zhiguang
 * date    : 2018/6/11
 */

@Controller
@RequestMapping(value = "/engine")
public class EngineController {

    @Autowired
    EngineService engineService;

    @Autowired
    TestCrossService tcService;

    //向外部提供数据集服务
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public String getResult(@RequestBody ParamHolder holder) {
        ResultSet rs = engineService.executeStatement(holder.getConnectionInfo(),engineService.parse(holder.getClauseMeta()));
        return engineService.wrapResult(rs, holder.getClauseMeta(), holder.getHisMaster().getGrapcode());
    }


    //向外部提供语句生成服务
    //测试，非正式，controller无业务逻辑
    @RequestMapping(value = "/smt", method = RequestMethod.POST)
    @ResponseBody
    public String getSqlStatment(@RequestParam Map<String, String>  clausemeta) throws JSONException {
        JSONObject jsonObject = new JSONObject(clausemeta);
        Gson gson = new Gson();
        ClauseMeta cm = gson.fromJson(jsonObject.get("clausemeta").toString(),ClauseMeta.class);
        return engineService.parse(cm);
    }


    //数据预览
    //需求调整的可能性较大；调用次数频繁，不支持大规模RDMBMS数据集和大数据数据源
    @RequestMapping(value = "/pvw", method = RequestMethod.POST)
    @ResponseBody
    public String preview(@RequestBody  ConnectionInfo conninfo) {
        return engineService.preview(conninfo, new ClauseMeta());
    }


    //测试db接口
    @RequestMapping(value = "/ts", method = RequestMethod.GET)
    @ResponseBody
    public String testdb() {
        DataBaseClient client = new DataBaseClient();
        client.getTmpConnection();
        client.closedbconnection();
        return tcService.test("msg");
    }



    //测试db insert接口
    @RequestMapping(value = "/ist", method = RequestMethod.GET)
    @ResponseBody
    public String[] testdb2() throws IlegalColumException {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setDbType("oracle");
        connectionInfo.setDbName("bigdata");
        connectionInfo.setIpAddr("172.16.16.151");
        connectionInfo.setService("orcl");
        connectionInfo.setPwd("123456");
        connectionInfo.setUname("TEST_VISUAL");
        connectionInfo.setPort(1521);

        ClauseMeta clauseMeta = new ClauseMeta();

       /* Column column1 = new Column();
        column1.setColumnName("province");
        column1.setTableName("tmpusers");

        Column column2 = new Column();
        column2.setColumnName("gender");
        column2.setTableName("tmpusers");

        Column column3 = new Column();
        column3.setColumnName("age");
        column3.setTableName("tmpusers");

        List selectlist = new ArrayList();
        selectlist.add(column1);
        selectlist.add(column2);
        selectlist.add(column3);
        clauseMeta.setSelectdims(selectlist);

        Func func1 = new Func();
        func1.setFuncname("sum");
        func1.setMeasurename("playtime");
        func1.setTableName("tmpusers");

        List funclist = new ArrayList();
        funclist.add(func1);
        clauseMeta.setFunclist(funclist);
        clauseMeta.setMaintable("tmpusers");
        clauseMeta.setGroupdims(selectlist);

        List orderlist = new ArrayList();
        OrderByy order1 = new OrderByy(column1,"desc");
        OrderByy order2 = new OrderByy(column2,"desc");
        OrderByy order3 = new OrderByy(column3,"desc");

        orderlist.add(order1);
        orderlist.add(order2);
        orderlist.add(order3);
        clauseMeta.setOrders(orderlist);

        //WhereTree tree = produceTree();
        //clauseMeta.setWheretree(tree);

        clauseMeta.setLimitamount(7);
        String[] arr = engineService.wrapAndInsert(connectionInfo,clauseMeta,"tm2018071600067a","aaaaaaaaaa12345");*/



        //测试日期
        Column column1 = new Column();
        column1.setColumnName("createtime");
        column1.setTableName("tmp_country");
        column1.setColumnType("date");
        List selectlist = new ArrayList();
        selectlist.add(column1);

        Func func1 = new Func();
        func1.setFuncname("sum");
        func1.setMeasurename("GDP");
        func1.setTableName("tmp_country");
        List funclist = new ArrayList();
        funclist.add(func1);

        List orderlist = new ArrayList();
        OrderByy order1 = new OrderByy(column1,"desc");
        orderlist.add(order1);

        clauseMeta.setMaintable("tmp_country");
        clauseMeta.setSelectdims(selectlist);
        clauseMeta.setFunclist(funclist);
        clauseMeta.setGroupdims(selectlist);
        clauseMeta.setOrders(orderlist);
        clauseMeta.setLimitamount(5);
        String[] arr = engineService.wrapAndInsert(connectionInfo,clauseMeta,"BITMP_20180724151101","aaaaaaaaaa12345");

        return arr;
    }

}
