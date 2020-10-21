package openapi.muye.uco.mysql.scripts;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.InstanceManage.*;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import static openapi.muye.uco.mysql.scripts.CreateInstance.getInstanceIdCommon;
import  static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.given;

public class InstanceManage {
    static String url = ConfigValue.url;
//    String instanceId =ConfigValue.getInstanceId();
//    String instanceId = "mysql-xzuvukain4e";
    static String RegionId = ConfigValue.RegionId;

    @Test(dataProvider = "RenewDBInstance", dataProviderClass = openapi.muye.uco.mysql.dataprovider.InstanceManageProvider.class)
    public void Test_RenewDBInstance(String Engine,String InstanceMode,int UsedTime, String Period) throws Exception {
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        getDBInstanceModel.setPayType("YEAR_MONTH");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        if(instanceId == null){
            System.out.println("未找到InstanceId，请检查参数是否正确");
            Assert.fail();
            return;
        }
        RenewDBInstanceModel renewDBInstanceModel = new RenewDBInstanceModel();
        renewDBInstanceModel.setDBInstanceId(instanceId);
        renewDBInstanceModel.setUsedTime(UsedTime);
        renewDBInstanceModel.setPeriod(Period);
        RenewDBInstance(renewDBInstanceModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
    }



    @Test(dataProvider = "RenewDBInstanceInvalid", dataProviderClass = openapi.muye.uco.mysql.dataprovider.InstanceManageProvider.class)
    public void Test_RenewDBInstanceInvalid(String Engine,String InstanceMode,int UsedTime, String Period, String ErrorCode) throws Exception {
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        getDBInstanceModel.setPayType("YEAR_MONTH");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        if(instanceId == null){
            System.out.println("未找到InstanceId，请检查参数是否正确");
            Assert.fail();
            return;
        }
        RenewDBInstanceModel renewDBInstanceModel = new RenewDBInstanceModel();
        renewDBInstanceModel.setDBInstanceId(instanceId);
        renewDBInstanceModel.setUsedTime(UsedTime);
        renewDBInstanceModel.setPeriod(Period);
        RenewDBInstance(renewDBInstanceModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode", is(ErrorCode));
    }

    @Test(dataProvider = "EngineMode", dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class,priority = 5)
    public void Test_RestartDBInstance(String Engine,String InstanceMode) throws Exception {
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        if(instanceId == null){
            System.out.println("未找到InstanceId，请检查参数是否正确");
            Assert.fail();
            return;
        }
        RestartDBInstanceModel restartDBInstanceModel = new RestartDBInstanceModel();
        restartDBInstanceModel.setDBInstanceId(instanceId);
        RestartDBInstance(restartDBInstanceModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
        //查询实例详情
        DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel = new DescribeDBInstanceAttributeModel();
        describeDBInstanceAttributeModel.setDBInstanceId(instanceId);
        Response getDBInstanceAttribute = DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
        getDBInstanceAttribute.then().log().all()
                .assertThat()
                .statusCode(200)
                .body("InstanceStatus", is("restarting"));
        //等待30s
        Thread.sleep(30000);
        int count = 1;
        while (count != 5) {
            Response getDBInstanceAttributeWait = DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
            getDBInstanceAttributeWait.then().log().all();
            String status = getDBInstanceAttributeWait.getBody().jsonPath().getString("InstanceStatus");
            System.out.println("当前实例状态是:"+status);
            if (status.equals("restarting")) {
                Thread.sleep(10000);
                count += 1;
            }
            if (status.equals("running")) {
                count = 5;
            }

        }
        Response getDBInstanceAttributeAfterWait = DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
        getDBInstanceAttributeAfterWait.then().log().all()
                .assertThat()
                .statusCode(200)
                .body("InstanceStatus", is("running"));

    }

    @Test(dataProvider = "EngineMode", dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class,priority = 10)
    public void Test_DeleteDBInstance(String Engine,String InstanceMode) throws Exception {
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        if(instanceId == null){
            System.out.println("未找到InstanceId，请检查参数是否正确");
            Assert.fail();
            return;
        }
        DeleteDBInstanceModel deleteDBInstanceModel = new DeleteDBInstanceModel();
        deleteDBInstanceModel.setDBInstanceId(instanceId);

        DeleteDBInstance(deleteDBInstanceModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
    }


        @Test
    public  void Test_DescribeDBInstances() throws Exception {
        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
        describeDBInstancesModel.setRegionId(RegionId);
       DescribeDBInstances(describeDBInstancesModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }

//    @Test
//    public void Test_DescribeDBInstanceAttribute() throws Exception {
//        /**
//         * @ Author :xx
//         * @ Date : Created 8:26 2020/7/30
//         * @ Param :[]
//         * @ Return : void
//         * @ Description: 获取实例详情，不单独做测试
//         */
//        DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel = new DescribeDBInstanceAttributeModel();
////        describeDBInstanceAttributeModel.setDBInstanceId(instanceId);
//        DescribeDBInstanceAttribute(describeDBInstanceAttributeModel)
//                .then()
//                .log().all()
//                .assertThat()
//                .statusCode(200)
//                .body("RequestId",notNullValue());
//    }
    public Response DeleteDBInstance( DeleteDBInstanceModel deleteDBInstanceModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 13:16 2020/7/30
         * @ Param :[deleteDBInstanceModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 删除实例
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", deleteDBInstanceModel.getAction());
        jsonParam.put("DBInstanceId", deleteDBInstanceModel.getDBInstanceId());

        String uri = url + ToSignUtil.getUrlNew("DELETE", jsonParam);
        return given().urlEncodingEnabled(false).log().all()
                .when()
                .delete(uri);
    }

    public Response RestartDBInstance( RestartDBInstanceModel restartDBInstanceModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 11:21 2020/7/30
         * @ Param :[restartDBInstanceModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 重启实例
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", restartDBInstanceModel.getAction());
        jsonParam.put("DBInstanceId", restartDBInstanceModel.getDBInstanceId());

        String uri = url + ToSignUtil.getUrlNew("POST", jsonParam);
        //重启实例
        return given().urlEncodingEnabled(false).log().all()
                .when()
                .post(uri);
    }

    public Response DescribeDBInstanceAttribute(DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 8:26 2020/7/30
         * @ Param :[]
         * @ Return : void
         * @ Description: 获取实例详情
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",describeDBInstanceAttributeModel.getAction());
        jsonParam.put("DBInstanceId",describeDBInstanceAttributeModel.getDBInstanceId());
        String uri = url + ToSignUtil.getUrlNew("GET",jsonParam);
        return given().urlEncodingEnabled(false).log().all()
                .when()
                .get(uri);
    }

    public  Response RenewDBInstance  (RenewDBInstanceModel renewDBInstanceModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 10:55 2020/7/30
         * @ Param :[renewDBInstanceModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 实例续费
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",renewDBInstanceModel.getAction());
        jsonParam.put("DBInstanceId",renewDBInstanceModel.getDBInstanceId());

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("UsedTime",renewDBInstanceModel.getUsedTime());
        jsonBody.put("Period",renewDBInstanceModel.getPeriod());

        String uri = url + ToSignUtil.getUrlNew("PUT",jsonParam);

        return given().urlEncodingEnabled(false).log().all()
                .contentType("application/json")
                .body(jsonBody.toJSONString())
                .when()
                .put(uri);
    }

    public static Response DescribeDBInstances(DescribeDBInstancesModel describeDBInstancesModel) {
        /**
         * @ Author :xx
         * @ Date : Created 10:39 2020/7/30
         * @ Param :[describeDBInstancesModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 查询实例列表
         */
        describeDBInstancesModel.setRegionId(RegionId);

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",describeDBInstancesModel.getAction());
        jsonParam.put("PageSize",describeDBInstancesModel.getPageSize());
        jsonParam.put("PageNumber",describeDBInstancesModel.getPageNumber());
        jsonParam.put("RegionId",describeDBInstancesModel.getRegionId());
        String uri = "";
        try {
            uri = url + ToSignUtil.getUrlNew("GET", jsonParam);
        }catch (Exception e){
            e.printStackTrace();
        }
        Response response =    given().urlEncodingEnabled(false).log().all()
                .when()
                .get(uri);
       return response;

    }

    public static String getInstanceId(String RegionId,String InstanceMode) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 10:50 2020/7/30
         * @ Param :[RegionId, InstanceMode]
         * @ Return : java.lang.String
         * @ Description: 根据区域ID和InstanceMode获取一个需要的实例ID
         */
        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
        describeDBInstancesModel.setRegionId(RegionId);
        Response response = new InstanceManage().DescribeDBInstances(describeDBInstancesModel);
        response.then().log().all();
        JSONArray jsonArray = JsonPath.read(response.asString(),
                "Items[?(@.InstanceMode == \""+InstanceMode+"\" && @.Engine == \"mysql\" && @.InstanceStatus == \"running\" && @.InstanceType == \"normal\")].DBInstanceId");
        if(jsonArray.isEmpty()){
            System.out.println("未找到instanceId，请确认是否有对应的实例");
            return "未找到instanceId，请确认是否有对应的实例";
        }
       return jsonArray.get(0).toString();
    }

//    public static String getInstanceIdCommon(GetDBInstanceModel getDBInstanceModel) throws Exception {
//        /**
//         * @ Author :xx
//         * @ Date : Created 10:50 2020/7/30
//         * @ Param :[RegionId, InstanceMode]
//         * @ Return : java.lang.String
//         * @ Description: 获取一个需要的实例ID
//         */
//        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
//        describeDBInstancesModel.setRegionId(getDBInstanceModel.getRegionId());
//        Response response =  InstanceManage.DescribeDBInstances(describeDBInstancesModel);
//        response.then().log().all();
////        JSONArray jsonArray = JsonPath.read(response.asString(),
////                "Items[?(@.InstanceMode == \""+getDBInstanceModel.getInstanceMode()+"\"" +
////                        " && @.Engine == \""+getDBInstanceModel.getEngine()+"\" && @.InstanceStatus == \"running\" " +
////                        "&& @.PayType == \""+getDBInstanceModel.getPayType()+"\""+
////                        "&& @.InstanceType == \""+getDBInstanceModel.getInstanceType()+"\")].DBInstanceId");
//
//        String reg = "Items[?(@.InstanceMode == \""+getDBInstanceModel.getInstanceMode()+"\" && @.InstanceStatus == \"running\"";
//        String end = ")].DBInstanceId";
//        String mid = "";
//        if(!getDBInstanceModel.getPayType().isEmpty()){
//            mid += " && @.PayType == \""+getDBInstanceModel.getPayType()+"\"";
//        }
//        if(!getDBInstanceModel.getEngine().isEmpty()){
//            mid += " && @.Engine == \""+getDBInstanceModel.getEngine()+"\"";
//        }
//        if(!getDBInstanceModel.getInstanceType().isEmpty()){
//            mid += " && @.InstanceType == \""+getDBInstanceModel.getInstanceType()+"\"";
//        }
//        System.out.println(reg+mid+end);
//        JSONArray jsonArray = JsonPath.read(response.asString(),reg+mid+end);
////        JSONArray jsonArray = JsonPath.read(response.asString(),"Items[?(@.InstanceMode == \"HA\" && @.InstanceStatus == \"running\" && @.Engine == \"mysql\")].DBInstanceId");
//        if(jsonArray.isEmpty()){
////            System.out.println("未找到instanceId，请确认是否有对应的实例");
//            return "未找到instanceId，请确认是否有对应的实例";
//        }
//        return jsonArray.get(0).toString();
//    }
//    @Test
    public void test2() throws Exception {
//        System.out.println(getInstanceId(RegionId,"SE"));
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setEngine("mysql");
        getDBInstanceModel.setInstanceMode("HA");
//        getDBInstanceModel.setInstanceType("read_only");
        getDBInstanceModel.setPayType("CHARGING_HOURS");
        System.out.println( getInstanceIdCommon(getDBInstanceModel));
    }

//    @Test
    public void test1() throws Exception {

    }

}
