package openapi.muye.uco.mysql.scripts;


import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.InstanceManage.CreateDBInstanceModel;
import openapi.muye.uco.mysql.models.InstanceManage.DescribeDBInstancesModel;
import openapi.muye.uco.mysql.models.InstanceManage.GetDBInstanceModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.annotations.Test;
import net.minidev.json.JSONArray;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @ Author :xx
 * @ Date : Created 21:22 2020/7/29
 * @ Description: 创建实例
 */
public class CreateInstance {
    String url = ConfigValue.url;
    @Test(dataProvider = "CreateInstance",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CreateInstanceProvider.class)
    public void Test_CreateDBInstance(String Engine,String RegionId,int Port,String EngineVersion,String AzId,String VpcId,
                                      String InstanceMode,String InstanceType,String VpcSubnetId,String PayType,
                                      int UsedTime,String Period,String RenewType,
                                      String AzIdSlave,String SourceInstanceId) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 22:38 2020/7/29
         * @ Param :[Engine, RegionId, Port, EngineVersion, AzId, VpcId, InstanceMode, InstanceType, VpcSubnetId, PayType, UsedTime, Period, RenewType, AzIdSlave, SourceInstanceId]
         * @ Return : void
         * @ Description: 创建三种不同计费方式的实例
         */
        CreateDBInstanceModel createDBInstanceModel = new CreateDBInstanceModel();
        createDBInstanceModel.setEngine(Engine);
        createDBInstanceModel.setRegionId(RegionId);
        createDBInstanceModel.setPort(Port);
        createDBInstanceModel.setEngineVersion(EngineVersion);
        createDBInstanceModel.setAzId(AzId);
        createDBInstanceModel.setVpcId(VpcId);
        createDBInstanceModel.setInstanceMode(InstanceMode);
        createDBInstanceModel.setInstanceType(InstanceType);
        createDBInstanceModel.setVpcSubnetId(VpcSubnetId);
        createDBInstanceModel.setPayType(PayType);
        createDBInstanceModel.setAzIdSlave(AzIdSlave);
        createDBInstanceModel.setSourceInstanceId(SourceInstanceId);
        createDBInstanceModel.setUsedTime(UsedTime);
        createDBInstanceModel.setPeriod(Period);
        createDBInstanceModel.setRenewType(RenewType);

        CreateDBInstance(createDBInstanceModel).then().log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
        Thread.sleep(60000);
    }
    @Test(dataProvider = "CreateInstanceReadOnly",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CreateInstanceProvider.class)
    public void Test_CreateDBInstanceReadOnly(String Engine,String RegionId,int Port,String EngineVersion,String AzId,String VpcId,
                                      String InstanceMode,String InstanceType,String VpcSubnetId,String PayType,
                                      int UsedTime,String Period,String RenewType,
                                      String AzIdSlave) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 22:38 2020/7/29
         * @ Param :[Engine, RegionId, Port, EngineVersion, AzId, VpcId, InstanceMode, InstanceType, VpcSubnetId, PayType, UsedTime, Period, RenewType, AzIdSlave]
         * @ Return : void
         * @ Description: 创建只读实例
         */
        //查询得到一个HA的instanceID

        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
        describeDBInstancesModel.setRegionId(RegionId);
        Response response = new InstanceManage().DescribeDBInstances(describeDBInstancesModel);
        response.then().log().all();
        JSONArray jsonArray = JsonPath.read(response.asString(),"Items[?(@.InstanceMode == \"HA\" && @.Engine == \"mysql\" && @.InstanceStatus == \"running\")].DBInstanceId");
        if(jsonArray.isEmpty()){
            System.out.println("未找到instanceId，请确认是否有对应的实例");
            return;
        }
        String SourceInstanceId = jsonArray.get(0).toString();
        CreateDBInstanceModel createDBInstanceModel = new CreateDBInstanceModel();
        createDBInstanceModel.setEngine(Engine);
        createDBInstanceModel.setRegionId(RegionId);
        createDBInstanceModel.setPort(Port);
        createDBInstanceModel.setEngineVersion(EngineVersion);
        createDBInstanceModel.setAzId(AzId);
        createDBInstanceModel.setVpcId(VpcId);
        createDBInstanceModel.setInstanceMode(InstanceMode);
        createDBInstanceModel.setInstanceType(InstanceType);
        createDBInstanceModel.setVpcSubnetId(VpcSubnetId);
        createDBInstanceModel.setPayType(PayType);
        createDBInstanceModel.setAzIdSlave(AzIdSlave);
        createDBInstanceModel.setSourceInstanceId(SourceInstanceId);
        createDBInstanceModel.setUsedTime(UsedTime);
        createDBInstanceModel.setPeriod(Period);
        createDBInstanceModel.setRenewType(RenewType);

        CreateDBInstance(createDBInstanceModel).then().log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
        Thread.sleep(60000);
    }

    public Response CreateDBInstance(CreateDBInstanceModel createDBInstanceModel ) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 22:05 2020/7/29
         * @ Param :[createDBInstanceModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 创建实例
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",createDBInstanceModel.getAction());

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("Engine",createDBInstanceModel.getEngine());
        jsonBody.put("RegionId",createDBInstanceModel.getRegionId());
        jsonBody.put("Port",createDBInstanceModel.getPort());
        jsonBody.put("EngineVersion",createDBInstanceModel.getEngineVersion());
        jsonBody.put("AzId",createDBInstanceModel.getAzId());
        jsonBody.put("InstanceMode",createDBInstanceModel.getInstanceMode());
        jsonBody.put("InstanceType",createDBInstanceModel.getInstanceType());
        jsonBody.put("VpcId",createDBInstanceModel.getVpcId());
        jsonBody.put("VpcSubnetId",createDBInstanceModel.getVpcSubnetId());
        jsonBody.put("InstanceClass",createDBInstanceModel.getInstanceClass());
        jsonBody.put("InstanceStorage",createDBInstanceModel.getInstanceStorage());
        jsonBody.put("InstanceStorageType",createDBInstanceModel.getInstanceStorageType());
        jsonBody.put("PayType",createDBInstanceModel.getPayType());
        jsonBody.put("Quantity",createDBInstanceModel.getQuantity());
        if (createDBInstanceModel.getPayType().equals("YEAR_MONTH")) {
            jsonBody.put("UsedTime", createDBInstanceModel.getUsedTime());
            jsonBody.put("Period", createDBInstanceModel.getPeriod());
            jsonBody.put("RenewType", createDBInstanceModel.getRenewType());
        }
        if(createDBInstanceModel.getInstanceMode().equals("HA")){
            jsonBody.put("AzIdSlave", createDBInstanceModel.getAzIdSlave());
        }
        if(createDBInstanceModel.getInstanceType().equals("read_only")){
            jsonBody.put("SourceInstanceId",createDBInstanceModel.getSourceInstanceId());
        }
        String uri = url + ToSignUtil.getUrlNew("POST",jsonParam);
//        System.out.println(jsonBody.toJSONString());
        Response response =
                given().urlEncodingEnabled(false)
                        .log().all()
                        .body(jsonBody.toJSONString())
                        .contentType("application/json")
                        .when()
                        .post(uri);
        return response;

    }

    public static String getInstanceIdCommon(GetDBInstanceModel getDBInstanceModel){
        /**
         * @ Author :xx
         * @ Date : Created 10:50 2020/7/30
         * @ Param :[RegionId, InstanceMode]
         * @ Return : java.lang.String
         * @ Description: 获取一个需要的实例ID
         */
        DescribeDBInstancesModel describeDBInstancesModel = new DescribeDBInstancesModel();
        describeDBInstancesModel.setRegionId(getDBInstanceModel.getRegionId());
        Response response =  InstanceManage.DescribeDBInstances(describeDBInstancesModel);
        response.then().log().all();
//        JSONArray jsonArray = JsonPath.read(response.asString(),
//                "Items[?(@.InstanceMode == \""+getDBInstanceModel.getInstanceMode()+"\"" +
//                        " && @.Engine == \""+getDBInstanceModel.getEngine()+"\" && @.InstanceStatus == \"running\" " +
//                        "&& @.PayType == \""+getDBInstanceModel.getPayType()+"\""+
//                        "&& @.InstanceType == \""+getDBInstanceModel.getInstanceType()+"\")].DBInstanceId");

        String reg = "Items[?(@.InstanceMode == \""+getDBInstanceModel.getInstanceMode()+"\" && @.InstanceStatus == \"running\"";
        String end = ")].DBInstanceId";
        String mid = "";
//        System.out.println("====="+getDBInstanceModel.getPayType());
        if(!getDBInstanceModel.getPayType().isEmpty()){
            mid += " && @.PayType == \""+getDBInstanceModel.getPayType()+"\"";
        }
        if(!getDBInstanceModel.getEngine().isEmpty()){
            mid += " && @.Engine == \""+getDBInstanceModel.getEngine()+"\"";
        }
        if(!getDBInstanceModel.getInstanceType().isEmpty()){
            mid += " && @.InstanceType == \""+getDBInstanceModel.getInstanceType()+"\"";
        }
        System.out.println(reg+mid+end);
        JSONArray jsonArray = JsonPath.read(response.asString(),reg+mid+end);
//        JSONArray jsonArray = JsonPath.read(response.asString(),"Items[?(@.InstanceMode == \"HA\" && @.InstanceStatus == \"running\" && @.Engine == \"mysql\")].DBInstanceId");
        if(jsonArray.isEmpty()){
            System.out.println("未找到instanceId，请确认是否有对应的实例");
//            return "未找到instanceId，请确认是否有对应的实例";
            return null;
        }
        System.out.println("当前instanceId:"+jsonArray.get(0).toString());
        return jsonArray.get(0).toString();
    }

}
