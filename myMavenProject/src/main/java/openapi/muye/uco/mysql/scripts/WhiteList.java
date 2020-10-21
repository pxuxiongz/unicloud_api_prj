package openapi.muye.uco.mysql.scripts;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.InstanceManage.DescribeDBInstanceAttributeModel;
import openapi.muye.uco.mysql.models.InstanceManage.GetDBInstanceModel;
import openapi.muye.uco.mysql.models.WhiteList.AddWhiteListModel;
import openapi.muye.uco.mysql.models.WhiteList.DeleteWhiteListModel;
import openapi.muye.uco.mysql.models.WhiteList.DescribeWhiteListModel;
import openapi.muye.uco.mysql.models.WhiteList.ModifyWhiteListModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class WhiteList {
    String url = ConfigValue.url;
//    String instanceId =ConfigValue.getInstanceId();

    @Test(dataProvider = "AddWhiteListOK", dataProviderClass = openapi.muye.uco.mysql.dataprovider.WhiteListDataProvider.class)
    public void Test_AddWhiteList(String Engine,String InstanceMode,String groupName, String ipList) throws Exception {
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
        clearWhiteList(instanceId);
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        addWhiteListModel.setGroupName(groupName);
        addWhiteListModel.setIPList(ipList);
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void Test_AddWhiteListGroupFull(String Engine,String InstanceMode) throws Exception {

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
        clearWhiteList(instanceId);
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        for(int i = 1;i<=4;i++){
            String ipList = "1.1.1."+i;
            String groupName = "autotest_"+i;
            addWhiteListModel.setGroupName(groupName);
            addWhiteListModel.setIPList(ipList);
            AddWhiteList(addWhiteListModel)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("RequestId", notNullValue());
        }

        addWhiteListModel.setGroupName("error1");
        addWhiteListModel.setIPList("1.1.1.100");
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode", is("GroupName.Invalid"));
    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void Test_AddWhiteListInnerIP(String Engine,String InstanceMode) throws Exception {
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
        DescribeWhiteListModel describeWhiteListModel = new DescribeWhiteListModel();
        describeWhiteListModel.setDBInstanceId(instanceId);
        Response response = DescribeWhiteList(describeWhiteListModel);
        response.then().log().all();
        String groupId = response.getBody().jsonPath().get("Items[-1].GroupId");
        System.out.printf("要修改的组名是%s，要修改的组ID是%s","default",groupId);
        ModifyWhiteListModel modifyWhiteListModel = new ModifyWhiteListModel();
        modifyWhiteListModel.setDBInstanceId(instanceId);
        modifyWhiteListModel.setGroupName("default");
        modifyWhiteListModel.setIPList("100.100.0.0");
        modifyWhiteListModel.setGroupId(groupId);
        ModifyWhiteList(modifyWhiteListModel)
                .then()
                .log().all()
                .statusCode(200);
        DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel = new DescribeDBInstanceAttributeModel();
        describeDBInstanceAttributeModel.setDBInstanceId(instanceId);
        Response getDBInstanceAttribute = new InstanceManage().DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
        getDBInstanceAttribute.then().log().all();
        String vip = getDBInstanceAttribute.getBody().jsonPath().getString("Vip");
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        addWhiteListModel.setGroupName("error1");
        addWhiteListModel.setIPList(vip);
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode", is("IP.Reserved"))
                .body("ErrorMessage",is("添加或修改的IP中存在属于内部使用的保留IP"));
    }

    @Test(dataProvider = "AddWhiteListGroupNameInvalid",dataProviderClass = openapi.muye.uco.mysql.dataprovider.WhiteListDataProvider.class)
    public void Test_AddWhiteListGroupNameInvalid(String Engine,String InstanceMode,String groupName, String ipList) throws Exception {
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
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        addWhiteListModel.setGroupName(groupName);
        addWhiteListModel.setIPList(ipList);
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode", is("GroupName.Invalid"));
    }

    @Test(dataProvider = "AddWhiteListIpInvalid",dataProviderClass = openapi.muye.uco.mysql.dataprovider.WhiteListDataProvider.class)
    public void Test_AddWhiteListIpInvalid(String Engine,String InstanceMode,String groupName, String ipList,String ErrorCode) throws Exception {
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
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        addWhiteListModel.setGroupName(groupName);
        addWhiteListModel.setIPList(ipList);
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode", is(ErrorCode));
    }

    @Test(dataProvider = "AddWhiteListIpNumInvalid",dataProviderClass = openapi.muye.uco.mysql.dataprovider.WhiteListDataProvider.class)
    public void Test_AddWhiteListIpNumInvalid(String Engine,String InstanceMode,String groupName, String ipList) throws Exception {
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
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        addWhiteListModel.setGroupName(groupName);
        addWhiteListModel.setIPList(ipList);
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode", is("IP.Num.Limit"));
    }
    @Test(dataProvider = "AddWhiteListNull",dataProviderClass = openapi.muye.uco.mysql.dataprovider.WhiteListDataProvider.class)
    public void Test_AddWhiteListNull(String Engine,String InstanceMode,String groupName, String ipList,String ErrorMessage,String ErrorCode) throws Exception {
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
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId(instanceId);
        addWhiteListModel.setGroupName(groupName);
        addWhiteListModel.setIPList(ipList);
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorMessage",is(ErrorMessage))
                .body("ErrorCode", is(ErrorCode));
    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void Test_ModifyWhiteList(String Engine,String InstanceMode) throws Exception {
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
        DescribeWhiteListModel describeWhiteListModel = new DescribeWhiteListModel();
        describeWhiteListModel.setDBInstanceId(instanceId);
        Response response = DescribeWhiteList(describeWhiteListModel);
        response.then().log().all();
        String groupName = response.getBody().jsonPath().getString("Items[-1].GroupName");
        String groupId = response.getBody().jsonPath().get("Items[-1].GroupId");
        System.out.printf("要修改的组名是%s，要修改的组ID是%s",groupName,groupId);
        System.out.println();
        ModifyWhiteListModel modifyWhiteListModel = new ModifyWhiteListModel();
        modifyWhiteListModel.setDBInstanceId(instanceId);
        modifyWhiteListModel.setGroupName(groupName);
        modifyWhiteListModel.setGroupId(groupId);
        modifyWhiteListModel.setIPList("1.0.0.0/0");
       ModifyWhiteList(modifyWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
        DescribeWhiteList(describeWhiteListModel).then()
                .log().all()
                .body("Items[-1].IPList", is(modifyWhiteListModel.getIPList()));
    }


//    @Test()
    public void Test_DeleteWhiteList() throws Exception {
        DescribeWhiteListModel describeWhiteListModel = new DescribeWhiteListModel();
        Response response = DescribeWhiteList(describeWhiteListModel);
        String groupName = response.getBody().jsonPath().getString("Items[-1].GroupName");
        DeleteWhiteListModel deleteWhiteListModel = new DeleteWhiteListModel();
//        deleteWhiteListModel.setDBInstanceId();
        DeleteWhiteList(deleteWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
    }


    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void Test_DescribeWhiteList(String Engine,String InstanceMode) throws Exception {
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);

        DescribeWhiteListModel describeWhiteListModel = new DescribeWhiteListModel();
        describeWhiteListModel.setDBInstanceId(instanceId);
        DescribeWhiteList(describeWhiteListModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId", notNullValue());
    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class,priority = 100)
    public void Test_ClearWhiteList(String Engine,String InstanceMode) throws Exception {

        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);

//        boolean flag = true;
//        while (flag) {
//            DescribeWhiteListModel describeWhiteListModel = new DescribeWhiteListModel();
//            describeWhiteListModel.setDBInstanceId(instanceId);
//            Response response = DescribeWhiteList(describeWhiteListModel);
//            response.then().log().all();
////            String groupName = response.getBody().jsonPath().getString("Items[-1].GroupName");
//            JSONArray groupNames = JsonPath.read(response.asString(),"Items[*].GroupName");
//            DeleteWhiteListModel deleteWhiteListModel = new DeleteWhiteListModel();
//            deleteWhiteListModel.setDBInstanceId(instanceId);
//            for(Object groupName:groupNames){
//                System.out.println("白名单组名是:"+groupName.toString());
//                deleteWhiteListModel.setGroupName(groupName.toString());
//                DeleteWhiteList(deleteWhiteListModel)
//                        .then()
//                        .log().all()
//                        .assertThat()
//                        .statusCode(200)
//                        .body("RequestId", notNullValue());
//            }
            clearWhiteList(instanceId);
//            System.out.println(groupName);
//            if (groupName.equals("default")) {
//                flag = false;
//            }
//        }

    }
    public  void clearWhiteList(String instanceId) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 17:31 2020/8/12
         * @ Param :[instanceId]
         * @ Return : void
         * @ Description: 清空白名单的方法
         */
        DescribeWhiteListModel describeWhiteListModel = new DescribeWhiteListModel();
        describeWhiteListModel.setDBInstanceId(instanceId);
        Response response = DescribeWhiteList(describeWhiteListModel);
        response.then().log().all();
//      String groupName = response.getBody().jsonPath().getString("Items[-1].GroupName");
        JSONArray groupNames = JsonPath.read(response.asString(),"Items[*].GroupName");
        DeleteWhiteListModel deleteWhiteListModel = new DeleteWhiteListModel();
        deleteWhiteListModel.setDBInstanceId(instanceId);
        for(Object groupName:groupNames) {
            System.out.println("白名单组名是:" + groupName.toString());
            deleteWhiteListModel.setGroupName(groupName.toString());
            DeleteWhiteList(deleteWhiteListModel)
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("RequestId", notNullValue());
        }
    }

    public Response AddWhiteList(AddWhiteListModel addWhiteListModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 16:46 2020/7/31
         * @ Param :[addWhiteListModel, groupName, ipList]
         * @ Return : io.restassured.response.Response
         * @ Description: 增加白名单
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", addWhiteListModel.getAction());
        jsonParam.put("DBInstanceId", addWhiteListModel.getDBInstanceId());

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("GroupName", addWhiteListModel.getGroupName());
        jsonBody.put("IPList", addWhiteListModel.getIPList());

        String uri = url + ToSignUtil.getUrlNew("POST", jsonParam);
       Response response =  given().urlEncodingEnabled(false).log().all()
                .body(jsonBody.toJSONString())
                .contentType("application/json")
                .when()
                .post(uri);
       return  response;
    }

    public Response ModifyWhiteList(ModifyWhiteListModel modifyWhiteListModel) throws Exception {

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", modifyWhiteListModel.getAction());
        jsonParam.put("DBInstanceId", modifyWhiteListModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("GroupName", modifyWhiteListModel.getGroupName());
        jsonBody.put("IPList", modifyWhiteListModel.getIPList());
        jsonBody.put("GroupId", modifyWhiteListModel.getGroupId());
        String uri = url + ToSignUtil.getUrlNew("PUT", jsonParam);
        return given().urlEncodingEnabled(false).log().all()
                .body(jsonBody.toJSONString())
                .contentType("application/json")
                .when()
                .put(uri);

    }
    public Response DescribeWhiteList(DescribeWhiteListModel describeWhiteListModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 16:51 2020/7/31
         * @ Param :[describeWhiteListModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 查询白名单列表
         */
//        describeWhiteListModel.setEngine(Engine);
//        describeWhiteListModel.setRegionId(RegionId);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", describeWhiteListModel.getAction());
        jsonParam.put("DBInstanceId", describeWhiteListModel.getDBInstanceId());
//        jsonParam.put("Engine",describeWhiteListModel.getEngine());
//        jsonParam.put("RegionId",describeWhiteListModel.getRegionId());
        String uri = url + ToSignUtil.getUrlNew("GET", jsonParam);
        Response response = given().urlEncodingEnabled(false).log().all()
                .when()
                .get(uri);
        return response;
    }

    public Response DeleteWhiteList(DeleteWhiteListModel deleteWhiteListModel) throws Exception {
//        deleteWhiteListModel.setDBInstanceId(instanceId);
//        deleteWhiteListModel.setGroupName(groupName);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", deleteWhiteListModel.getAction());
        jsonParam.put("DBInstanceId", deleteWhiteListModel.getDBInstanceId());
        jsonParam.put("GroupName", deleteWhiteListModel.getGroupName());

        String uri = url + ToSignUtil.getUrlNew("DELETE", jsonParam);
        Response response = given().urlEncodingEnabled(false).log().all()
                .when()
                .delete(uri);
        return response;
    }


//    @Test
    public void Test() throws Exception {
        AddWhiteListModel addWhiteListModel = new AddWhiteListModel();
        addWhiteListModel.setDBInstanceId("mysql-y6pbmhpa3q4");
        addWhiteListModel.setGroupName("error1");
        addWhiteListModel.setIPList("1.1.1.100");
        AddWhiteList(addWhiteListModel)
                .then()
                .log().all();

    }
}
