package openapi.muye.uco.redis.scripts;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import openapi.muye.uco.config.ConfigValue;

import openapi.muye.uco.redis.models.InstanceManage.DeleteDBInstanceModel;
import openapi.muye.uco.redis.models.InstanceManage.DescribeDBInstanceAttributeModel;
import openapi.muye.uco.redis.models.InstanceManage.DescribeDBInstancesModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.annotations.Test;
import  static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.given;

public class InstanceManage {
    static String url = ConfigValue.url;
    public static String RegionId = ConfigValue.RegionId;
    @Test(dataProvider = "EngineMode", dataProviderClass = openapi.muye.uco.redis.dataprovider.CommonProvider.class,priority = 10)
    public void Test_DeleteDBInstance(String InstanceMode,double Version)  {
        String instanceId = getInstanceId(RegionId,InstanceMode,Version);
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
    public  void Test_DescribeDBInstances() {
        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
        describeDBInstancesModel.setRegionId(RegionId);
        DescribeDBInstances(describeDBInstancesModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }
    @Test(dataProviderClass = openapi.muye.uco.redis.dataprovider.CommonProvider.class,dataProvider = "EngineMode")
    public void Test_DescribeDBInstanceAttribute(String InstanceMode,double Version){
        DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel = new DescribeDBInstanceAttributeModel();
        describeDBInstanceAttributeModel.setDBInstanceId(getInstanceId(RegionId,InstanceMode,Version));
        DescribeDBInstanceAttribute(describeDBInstanceAttributeModel)
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
    public Response DeleteDBInstance( DeleteDBInstanceModel deleteDBInstanceModel) {
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
        String uri = "";
        try {
            uri = url + ToSignUtil.getUrlNew("DELETE", jsonParam);
        }catch (Exception e){
            e.printStackTrace();
        }
        return given().urlEncodingEnabled(false).log().all()
                .when()
                .delete(uri);
    }



    public Response DescribeDBInstanceAttribute(DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel)  {
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
        String uri = "";
        try {
            uri = url + ToSignUtil.getUrlNew("GET", jsonParam);
        }catch (Exception e){
            e.printStackTrace();
        }
        return given().urlEncodingEnabled(false).log().all()
                .when()
                .get(uri);
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

    public static String getInstanceId(String RegionId,String InstanceMode,double Version){
        /**
         * @ Author :xx
         * @ Date : Created 10:50 2020/7/30
         * @ Param :[RegionId, InstanceMode]
         * @ Return : java.lang.String
         * @ Description: 根据区域ID和InstanceMode获取一个需要的实例ID
         */
        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
        describeDBInstancesModel.setRegionId(RegionId);
        Response response = DescribeDBInstances(describeDBInstancesModel);
        response.then().log().all();
        JSONArray jsonArray = JsonPath.read(response.asString(),
                "Items[?(@.InstanceMode == \""+InstanceMode+"\" && @.EngineVersion == \""+Version+"\" && @.Engine == \"redis\" && @.InstanceStatus == \"running\" && @.InstanceType == \"normal\")].DBInstanceId");
        if(jsonArray.isEmpty()){
            System.out.println("未找到instanceId，请确认是否有对应的实例");
            return "未找到instanceId，请确认是否有对应的实例";
        }
        return jsonArray.get(0).toString();
    }


}
