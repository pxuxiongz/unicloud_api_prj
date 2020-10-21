package openapi.muye.uco.mysql.scripts;


import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.Backup.CreateBackupModel;
import openapi.muye.uco.mysql.models.Backup.DescribeBackupPolicyModel;
import openapi.muye.uco.mysql.models.Backup.DescribeBackupsModel;
import openapi.muye.uco.mysql.models.Backup.ModifyBackupPolicyModel;
import openapi.muye.uco.mysql.models.InstanceManage.DescribeDBInstanceAttributeModel;
import openapi.muye.uco.mysql.models.InstanceManage.GetDBInstanceModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.given;
/**
 * @ Author :xx
 * @ Date : Created 11:31 2020/8/11
 * @ Description: 备份管理
 */
public class BackupManage {
    String url = ConfigValue.url;

    @Test(dataProvider = "ModifyBackupPolicy",dataProviderClass = openapi.muye.uco.mysql.dataprovider.BackupDataProvider.class)
    public void Test_ModifyBackupPolicy(String Engine,String InstanceMode,String BackupPeriod,String BackupTime){
        System.out.println(BackupPeriod+BackupTime+Engine+InstanceMode);
        //获取一个instanceID
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
        //设置备份规则参数
        ModifyBackupPolicyModel modifyBackupPolicyModel = new ModifyBackupPolicyModel();
        modifyBackupPolicyModel.setBackupPeriod(BackupPeriod);
        modifyBackupPolicyModel.setBackupTime(BackupTime);
        modifyBackupPolicyModel.setDBInstanceId(instanceId);
        ModifyBackupPolicy(modifyBackupPolicyModel).then()
                .log().all()
                .statusCode(200)
                .body("RequestId",notNullValue());
        //验证备份规则修改成功
        DescribeBackupPolicyModel describeBackupPolicyModel = new DescribeBackupPolicyModel();
        describeBackupPolicyModel.setDBInstanceId(instanceId);
        DescribeBackupPolicy(describeBackupPolicyModel).then()
                .log().all()
                .statusCode(200)
                .body("BackupPeriod",is(BackupPeriod))
                .body("BackupTime",is(BackupTime));
    }


    @Test(dataProvider = "ModifyBackupPolicyFail",dataProviderClass = openapi.muye.uco.mysql.dataprovider.BackupDataProvider.class)
    public void Test_ModifyBackupPolicyFail(String Engine,String InstanceMode,String BackupPeriod,String BackupTime,String ErrorMessage,String ErrorCode){
        System.out.println(BackupPeriod+BackupTime+Engine+InstanceMode);
        //获取一个instanceID
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
        //设置备份规则参数
        ModifyBackupPolicyModel modifyBackupPolicyModel = new ModifyBackupPolicyModel();
        modifyBackupPolicyModel.setBackupPeriod(BackupPeriod);
        modifyBackupPolicyModel.setBackupTime(BackupTime);
        modifyBackupPolicyModel.setDBInstanceId(instanceId);
        ModifyBackupPolicy(modifyBackupPolicyModel).then()
                .log().all()
                .statusCode(400)
                .body("ErrorMessage",is(ErrorMessage))
                .body("ErrorCode",is(ErrorCode));
    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void Test_CreateBackup(String Engine,String InstanceMode) throws Exception {
        //获取一个instanceID
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
//        instanceId = "mysql-y5wsex7xtv9";
        //手动备份
        CreateBackupModel createBackupModel = new CreateBackupModel();
        createBackupModel.setDBInstanceId(instanceId);
        CreateBackup(createBackupModel).then()
                    .log().all()
                    .statusCode(200);
        //验证实例状态是备份中
        //查询实例详情
        DescribeDBInstanceAttributeModel describeDBInstanceAttributeModel = new DescribeDBInstanceAttributeModel();
        describeDBInstanceAttributeModel.setDBInstanceId(instanceId);
        Response getDBInstanceAttribute = new InstanceManage().DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
        getDBInstanceAttribute.then().log().all()
                .assertThat()
                .statusCode(200)
                .body("InstanceStatus", is("backuping"));
        //等待30s
        Thread.sleep(30000);
        int count = 0;
        while (count != 10) {
            Response getDBInstanceAttributeWait = new InstanceManage().DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
            getDBInstanceAttributeWait.then().log().all();
            String status = getDBInstanceAttributeWait.getBody().jsonPath().getString("InstanceStatus");
            System.out.println("当前实例状态是:"+status);
            if (status.equals("backuping")) {
                Thread.sleep(20000);
                count += 1;
            }
            if (status.equals("running")) {
                count = 10;
            }

        }
        Response getDBInstanceAttributeAfterWait = new InstanceManage().DescribeDBInstanceAttribute(describeDBInstanceAttributeModel);
        getDBInstanceAttributeAfterWait.then().log().all()
                .assertThat()
                .statusCode(200)
                .body("InstanceStatus", is("running"));

        //下载备份
        //查询备份列表中该备份状态
        DescribeBackupsModel describeBackupsModel = new DescribeBackupsModel();
        describeBackupsModel.setDBInstanceId(instanceId);
        Response response = DescribeBackups(describeBackupsModel);
        response.then().log().all()
                .statusCode(200);
        String BackupDownloadURL =response.getBody().jsonPath().getString("Items[0].BackupDownloadURL");
        System.out.println(BackupDownloadURL);
        given().urlEncodingEnabled(false).log().all()
                .when()
                .get(BackupDownloadURL)
                .then()
                .statusCode(200);
    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void Test_DescribeBackups(String Engine,String InstanceMode) throws Exception {
        //获取一个instanceID
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
        DescribeBackupsModel describeBackupsModel = new DescribeBackupsModel();
        describeBackupsModel.setDBInstanceId(instanceId);
        Response response = DescribeBackups(describeBackupsModel);
        response.then().log().all()
                .statusCode(200);
        String BackupDownloadURL = response.getBody().jsonPath().getString("Items[0].BackupDownloadURL");
//        System.out.println(BackupDownloadURL);
//        given().urlEncodingEnabled(false).log().all()
//                .when()
//                .get(BackupDownloadURL)
//                .then()
//                .statusCode(200);
//                .log().all();
    }

    public Response DescribeBackupPolicy(DescribeBackupPolicyModel describeBackupPolicyModel){
        /**
         * @ Author :xx
         * @ Date : Created 11:40 2020/8/11
         * @ Param :[describeBackupPolicyModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 获取备份规则的方法
         */
        JSONObject jsonparams = new JSONObject();
        jsonparams.put("Action",describeBackupPolicyModel.getAction());
        jsonparams.put("DBInstanceId",describeBackupPolicyModel.getDBInstanceId());
        String uri = "";
        try {
            uri = url + ToSignUtil.getUrlNew("GET", jsonparams);
        }catch (Exception e){
            e.printStackTrace();
        }
        Response response = given().urlEncodingEnabled(false).log().all().
                when().
                get(uri);
        return  response;
    }

    public Response ModifyBackupPolicy(ModifyBackupPolicyModel modifyBackupPolicyModel){
        /**
         * @ Author :xx
         * @ Date : Created 11:47 2020/8/11
         * @ Param :[modifyBackupPolicyModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 设置备份规则
         */
        JSONObject jsonparams = new JSONObject();
        jsonparams.put("Action",modifyBackupPolicyModel.getAction());
        jsonparams.put("DBInstanceId",modifyBackupPolicyModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("BackupPeriod",modifyBackupPolicyModel.getBackupPeriod());
        jsonBody.put("BackupTime",modifyBackupPolicyModel.getBackupTime());
        String uri = "";
        try{
            uri = url + ToSignUtil.getUrlNew("PUT",jsonparams);
        }catch (Exception e){
            e.printStackTrace();
        }
        return given().urlEncodingEnabled(false).log().all()
                .contentType("application/json")
                .body(jsonBody.toJSONString())
                .when()
                .put(uri);
    }

    public Response CreateBackup(CreateBackupModel createBackupModel){
        /**
         * @ Author :xx
         * @ Date : Created 11:50 2020/8/11
         * @ Param :[createBackupModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 创建手动备份
         */
        JSONObject jsonparams = new JSONObject();
        jsonparams.put("Action",createBackupModel.getAction());
        jsonparams.put("DBInstanceId",createBackupModel.getDBInstanceId());
        String uri = "";
        try{
            uri = url + ToSignUtil.getUrlNew("POST",jsonparams);
        }catch (Exception e){
            e.printStackTrace();
        }
        return given().urlEncodingEnabled(false)
                .log().all()
                .contentType("application/json")
                .when()
                .post(uri);
    }
    public Response DescribeBackups(DescribeBackupsModel describeBackupsModel){
        /**
         * @ Author :xx
         * @ Date : Created 11:53 2020/8/11
         * @ Param :[describeBackupsModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 获取备份列表
         */
        JSONObject jsonparams = new JSONObject();
        jsonparams.put("Action",describeBackupsModel.getAction());
        jsonparams.put("DBInstanceId",describeBackupsModel.getDBInstanceId());
        jsonparams.put("PageSize",describeBackupsModel.getPageSize());
        jsonparams.put("PageNumber",describeBackupsModel.getPageNumber());
        String uri = "";
        try{
            uri = url + ToSignUtil.getUrlNew("GET",jsonparams);
        }catch(Exception e){
            e.printStackTrace();
        }
        return given().urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(uri);
    }

}
