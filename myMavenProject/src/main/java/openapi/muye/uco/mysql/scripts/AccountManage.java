package openapi.muye.uco.mysql.scripts;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import openapi.muye.uco.common.model.GetDBInstanceModel;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.AccountManage.*;
import openapi.muye.uco.mysql.models.DBManage.CreateDatabaseModel;
import openapi.muye.uco.mysql.models.DBManage.DeleteDatabaseModel;
import openapi.muye.uco.mysql.models.DBManage.DescribeDatabasesModel;
import openapi.muye.uco.util.CommonUtil;
import openapi.muye.uco.util.ToSignUtil;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
public class AccountManage {
     String url = ConfigValue.url;
//     String instanceId = ConfigValue.getInstanceId();

    @Test(dataProvider = "CreateAccount",dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class)
    public  void Test_CreateAccount(String Engine,String InstanceMode,String accountName,String password,String accountType) throws Exception {
//       GetDBInstanceModel getDBInstanceModel =  new GetDBInstanceModel();
//       getDBInstanceModel.setEngine(Engine);
//       getDBInstanceModel.setInstanceMode(Mode);
//        String  instanceId = InstanceManage.getInstanceIdCommon(getDBInstanceModel);
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
        CreateAccountModel createAccountModel = new CreateAccountModel();
        createAccountModel.setAccountPassword(password);
        createAccountModel.setDBInstanceId(instanceId);
        createAccountModel.setAccountType(accountType);
        createAccountModel.setAccountName(accountName);

        CreateAccount(createAccountModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }

    @Test(dataProvider = "CreateAccountInvalid",dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class
            ,dependsOnMethods = "Test_CreateAccount")
    public   void Test_CreateAccountInvalid(String Engine,String InstanceMode,String accountName,String password,String accountType,String ErrorCode) throws Exception {
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
        CreateAccountModel createAccountModel = new CreateAccountModel();
        createAccountModel.setAccountName(accountName);
        createAccountModel.setAccountPassword(password);
        createAccountModel.setAccountType(accountType);
        createAccountModel.setDBInstanceId(instanceId);
        CreateAccount(createAccountModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode",is(ErrorCode));
    }

//    @AfterClass
    public void Test_DeleteAccount(String Engine,String InstanceMode,String accountName) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 15:10 2020/7/29
         * @ Param :[]
         * @ Return : void
         * @ Description: 在清理环境中会调用该接口，因此不再单独测试该接口
         */
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        DeleteAccountModel deleteAccountModel = new DeleteAccountModel();
        deleteAccountModel.setDBInstanceId(instanceId);
        deleteAccountModel.setAccountName(accountName);

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",deleteAccountModel.getAction());
        jsonParam.put("DBInstanceId",deleteAccountModel.getDBInstanceId());
        jsonParam.put("AccountName",deleteAccountModel.getAccountName());
        String uri = url + ToSignUtil.getUrlNew("DELETE",jsonParam);
        System.out.println(uri);
        given().urlEncodingEnabled(false).log().all()
                .contentType("application/json")
                .when()
                .delete(uri)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }
//    @Test
    public void Test_DescribeAccounts() throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 15:10 2020/7/29
         * @ Param :[]
         * @ Return : void
         * @ Description: 在清理环境会调用删除，因此不在单独测试该接口
         */
        DescribeAccountsModel describeAccountsModel = new DescribeAccountsModel();
        DescribeAccounts(describeAccountsModel)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }
    @Test(dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class,
                dataProvider = "ResetAccount",dependsOnMethods = "Test_CreateAccount")
    public void Test_ResetAccount(String Engine,String InstanceMode,String password) throws Exception {
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
        DescribeAccountsModel describeAccountsModel = new DescribeAccountsModel();
        describeAccountsModel.setDBInstanceId(instanceId);
        Response response =  DescribeAccounts(describeAccountsModel);
        String accountName = response.getBody().jsonPath().getString("Items[0].AccountName");
        ResetAccountModel resetAccountModel = new ResetAccountModel();
        resetAccountModel.setDBInstanceId(instanceId);
        resetAccountModel.setAccountName(accountName);
        resetAccountModel.setAccountPassword(password);
        ResetAccount(resetAccountModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());

    }

    @Test(dependsOnMethods = "Test_CreateAccount",dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class,
            dataProvider = "ResetAccountInvalid")
    public void Test_ResetAccountInvalid(String Engine,String InstanceMode,String password,String ErrorCode) throws Exception {
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
        DescribeAccountsModel describeAccountsModel = new DescribeAccountsModel();
        describeAccountsModel.setDBInstanceId(instanceId);
        Response response =  DescribeAccounts(describeAccountsModel);
        String accountName = response.getBody().jsonPath().getString("Items[0].AccountName");
        ResetAccountModel resetAccountModel = new ResetAccountModel();
        resetAccountModel.setDBInstanceId(instanceId);
        resetAccountModel.setAccountName(accountName);
        resetAccountModel.setAccountPassword(password);
        ResetAccount(resetAccountModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode",is(ErrorCode));

    }


    @Test(dataProvider = "GrantAccountPrivilege",
            dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class)
    public void Test_GrantAccountPrivilege(String Engine,String InstanceMode,String privilege) throws Exception {

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
        //创建一个账号
        CreateAccountModel createAccountModel = new CreateAccountModel();
        createAccountModel.setDBInstanceId(instanceId);
        String accountName = "au"+CommonUtil.getTimeStamp();
        createAccountModel.setAccountName(accountName);
        createAccountModel.setAccountPassword("Admin#123");
        createAccountModel.setAccountType("normal");
        CreateAccount(createAccountModel);
//        //查询一个账号
//        DescribeAccountsModel describeAccountsModel = new DescribeAccountsModel();
//        Response response =  DescribeAccounts(describeAccountsModel);
//        String accountName = response.getBody().jsonPath().getString("Items[0].AccountName");

        //创建一个数据库
        DatabaseManage databaseManage = new DatabaseManage();
        CreateDatabaseModel createDatabaseModel = new CreateDatabaseModel();
        createDatabaseModel.setDBInstanceId(instanceId);
        createDatabaseModel.setCharacterSetName("utf8");
        String dbName = "auto"+CommonUtil.getTimeStamp();
        createDatabaseModel.setDBName(dbName);
        databaseManage.CreateDatabase(createDatabaseModel);

        GrantAccountPrivilegeModel grantAccountPrivilegeModel = new GrantAccountPrivilegeModel();
        grantAccountPrivilegeModel.setDBInstanceId(instanceId);
        grantAccountPrivilegeModel.setAccountName(accountName);
        grantAccountPrivilegeModel.setDBName(dbName);
        grantAccountPrivilegeModel.setAccountPrivilege(privilege);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",grantAccountPrivilegeModel.getAction());
        jsonParam.put("DBInstanceId",grantAccountPrivilegeModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("AccountName",grantAccountPrivilegeModel.getAccountName());
        JSONObject subJsonBody = new JSONObject();
        subJsonBody.put("DBName",grantAccountPrivilegeModel.getDBName());
        subJsonBody.put("AccountPrivilege",grantAccountPrivilegeModel.getAccountPrivilege());
        JSONArray list = new JSONArray();
        list.add(subJsonBody);
        jsonBody.put("GrantDBList",list);
        String uri = url + ToSignUtil.getUrlNew("PUT",jsonParam);
        GrantAccountPrivilege(grantAccountPrivilegeModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }
    @Test(dataProvider = "RevokeAccountPrivilege",dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class)
    public void Test_RevokeAccountPrivilege(String Engine,String InstanceMode,String dbName,String accountName,String privilege) throws Exception {
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
        //创建一个账号
        CreateAccountModel createAccountModel = new CreateAccountModel();
        createAccountModel.setDBInstanceId(instanceId);
        createAccountModel.setAccountName(accountName);
        createAccountModel.setAccountPassword("Admin#123");
        createAccountModel.setAccountType("normal");
        CreateAccount(createAccountModel);

        //创建一个数据库
        DatabaseManage databaseManage = new DatabaseManage();
        CreateDatabaseModel createDatabaseModel = new CreateDatabaseModel();
        createDatabaseModel.setDBName(dbName);
        createDatabaseModel.setCharacterSetName("utf8");
        createDatabaseModel.setDBInstanceId(instanceId);
        databaseManage.CreateDatabase(createDatabaseModel);

        //授权数据库
        GrantAccountPrivilegeModel grantAccountPrivilegeModel = new GrantAccountPrivilegeModel();
        grantAccountPrivilegeModel.setDBInstanceId(instanceId);
        grantAccountPrivilegeModel.setAccountName(accountName);
        grantAccountPrivilegeModel.setDBName(dbName);
        GrantAccountPrivilege(grantAccountPrivilegeModel);
        RevokeAccountPrivilegeModel revokeAccountPrivilegeModel = new RevokeAccountPrivilegeModel();
        revokeAccountPrivilegeModel.setDBInstanceId(instanceId);
        revokeAccountPrivilegeModel.setAccountName(accountName);
        revokeAccountPrivilegeModel.setRevokeDBList(dbName);
        //撤销授权
        RevokeAccountPrivilege(revokeAccountPrivilegeModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());

    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class)
    public void Test_RevokeAccountPrivilegeDBs(String Engine,String InstanceMode) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 20:37 2020/7/29
         * @ Param :[]
         * @ Return : void
         * @ Description: 撤销多个数据库的授权
         */

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
        //创建一个账号
        CreateAccountModel createAccountModel = new CreateAccountModel();
        String accountName = "au"+CommonUtil.getTimeStamp();
        createAccountModel.setAccountName(accountName);
        createAccountModel.setAccountPassword("Admin#123");
        createAccountModel.setAccountType("normal");
        createAccountModel.setDBInstanceId(instanceId);
        CreateAccount(createAccountModel);

        //创建一个数据库
        DatabaseManage databaseManage = new DatabaseManage();
        CreateDatabaseModel createDatabaseModel = new CreateDatabaseModel();
        createDatabaseModel.setDBInstanceId(instanceId);
        createDatabaseModel.setDBName("db1");
        createDatabaseModel.setCharacterSetName("utf8");
        databaseManage.CreateDatabase(createDatabaseModel);
        createDatabaseModel.setDBName("db2");
        databaseManage.CreateDatabase(createDatabaseModel);

        //授权数据库
        GrantAccountPrivilegeModel grantAccountPrivilegeModel = new GrantAccountPrivilegeModel();
        grantAccountPrivilegeModel.setDBInstanceId(instanceId);
        grantAccountPrivilegeModel.setAccountName(accountName);
        grantAccountPrivilegeModel.setDBName("db1");
        grantAccountPrivilegeModel.setAccountPrivilege("readOnly");
        GrantAccountPrivilege(grantAccountPrivilegeModel);
        grantAccountPrivilegeModel.setDBName("db2");
        grantAccountPrivilegeModel.setAccountPrivilege("readWrite");
        GrantAccountPrivilege(grantAccountPrivilegeModel);

        //撤销授权
        RevokeAccountPrivilegeModel revokeAccountPrivilegeModel = new RevokeAccountPrivilegeModel();
        revokeAccountPrivilegeModel.setDBInstanceId(instanceId);
        revokeAccountPrivilegeModel.setAccountName(accountName);
        revokeAccountPrivilegeModel.setRevokeDBList("db1,db2");
        RevokeAccountPrivilege(revokeAccountPrivilegeModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());

    }

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.AccountManageDataProvider.class,priority = 100)
    public void ClearAccount(String Engine,String InstanceMode) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 15:45 2020/7/31
         * @ Param :[Engine, InstanceMode]
         * @ Return : void
         * @ Description: 清理账号和DB
         */
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
        DescribeAccountsModel describeAccountsModel = new DescribeAccountsModel();
        describeAccountsModel.setDBInstanceId(instanceId);
        DeleteAccountModel deleteAccountModel = new DeleteAccountModel();
        deleteAccountModel.setDBInstanceId(instanceId);
        while(true) {
            Response response =  DescribeAccounts(describeAccountsModel);
            response.then().log().all();
            int count = response.getBody().jsonPath().getInt("TotalRecordCount");
            if(count == 0){
               break;
            }
            net.minidev.json.JSONArray jsonArrayAccount = JsonPath.read(response.asString(),"Items[*].AccountName");
//            String accountName = response.getBody().jsonPath().getString("Items[0].AccountName");
            for(Object accountNameObj:jsonArrayAccount) {
                String accountName = accountNameObj.toString();
                deleteAccountModel.setAccountName(accountName);
                DeleteAccount(deleteAccountModel).then().log().all()
                        .body("RequestId", notNullValue());
            }

        }
        //清理DB
        DatabaseManage databaseManage = new DatabaseManage();
        while(true){
            DescribeDatabasesModel describeDatabasesModel = new DescribeDatabasesModel();
            describeDatabasesModel.setDBInstanceId(instanceId);
            Response response = databaseManage.DescribeDatabases(describeDatabasesModel);
            response.then().log().all()
                    .assertThat()
                    .statusCode(200);
            int count = response.getBody().jsonPath().getInt("TotalRecordCount");
            if (count == 0){
               break;
            }
//            String dbName = response.getBody().jsonPath().getString("Items[0].DBName");
            net.minidev.json.JSONArray jsonArray = JsonPath.read(response.asString(),"Items[*].DBName");
            DeleteDatabaseModel deleteDatabaseModel = new DeleteDatabaseModel();
            deleteDatabaseModel.setDBInstanceId(instanceId);
            for(Object dbNameObject:jsonArray) {
                String dbName = dbNameObject.toString();
                deleteDatabaseModel.setDBName(dbName);
                databaseManage.DeleteDatabase(deleteDatabaseModel).then().log().all().
                        assertThat().
                        statusCode(200);
            }
        }
    }

    public Response RevokeAccountPrivilege(RevokeAccountPrivilegeModel revokeAccountPrivilegeModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 19:47 2020/7/29
         * @ Param :[dbName]
         * @ Return : io.restassured.response.Response
         * @ Description: 取消授权
         */
//        DescribeAccountsModel describeAccountsModel = new DescribeAccountsModel();
//        Response response =  DescribeAccounts(describeAccountsModel);
//        String accountName = response.getBody().jsonPath().getString("Items[0].AccountName");
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",revokeAccountPrivilegeModel.getAction());
        jsonParam.put("DBInstanceId",revokeAccountPrivilegeModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("AccountName",revokeAccountPrivilegeModel.getAccountName());
        jsonBody.put("RevokeDBList",revokeAccountPrivilegeModel.getRevokeDBList());
        String uri = url + ToSignUtil.getUrlNew("PUT",jsonParam);

        return given().urlEncodingEnabled(false)
                .log().all()
                .contentType("application/json")
                .body(jsonBody.toJSONString())
                .when()
                .put(uri);

    }

    public Response GrantAccountPrivilege(GrantAccountPrivilegeModel grantAccountPrivilegeModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 16:53 2020/7/29
         * @ Param :[grantAccountPrivilegeModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 给账号授权数据库
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",grantAccountPrivilegeModel.getAction());
        jsonParam.put("DBInstanceId",grantAccountPrivilegeModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("AccountName",grantAccountPrivilegeModel.getAccountName());
        JSONObject subJsonBody = new JSONObject();
        subJsonBody.put("DBName",grantAccountPrivilegeModel.getDBName());
        subJsonBody.put("AccountPrivilege",grantAccountPrivilegeModel.getAccountPrivilege());
        JSONArray list = new JSONArray();
        list.add(subJsonBody);
        jsonBody.put("GrantDBList",list);
        String uri = url + ToSignUtil.getUrlNew("PUT",jsonParam);
        return given().urlEncodingEnabled(false)
                .log().all()
                .contentType("application/json")
                .body(jsonBody.toJSONString())
                .when()
                .put(uri);
    }

    public Response ResetAccount( ResetAccountModel resetAccountModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 16:20 2020/7/29
         * @ Param :[resetAccountModel, accountName, password]
         * @ Return : io.restassured.response.Response
         * @ Description: 重置账号密码
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",resetAccountModel.getAction());
        jsonParam.put("DBInstanceId",resetAccountModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("AccountName",resetAccountModel.getAccountName());
        jsonBody.put("AccountPassword",resetAccountModel.getAccountPassword());
        String uri = url + ToSignUtil.getUrlNew("PUT",jsonParam);
        return  given().urlEncodingEnabled(false)
                .log().all()
                .contentType("application/json")
                .body(jsonBody.toJSONString())
                .when()
                .put(uri);

    }

    public Response DeleteAccount( DeleteAccountModel deleteAccountModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 15:13 2020/7/29
         * @ Param :[deleteAccountModel, accountName]
         * @ Return : io.restassured.response.Response
         * @ Description: 删除账号的接口
         */

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action", deleteAccountModel.getAction());
        jsonParam.put("DBInstanceId", deleteAccountModel.getDBInstanceId());
        jsonParam.put("AccountName", deleteAccountModel.getAccountName());
        String uri = url + ToSignUtil.getUrlNew("DELETE", jsonParam);
        System.out.println(uri);
        return given().urlEncodingEnabled(false).log().all()
                .contentType("application/json")
                .when()
                .delete(uri);
    }

    public Response DescribeAccounts(DescribeAccountsModel describeAccountsModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 15:12 2020/7/29
         * @ Param :[describeAccountsModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 查询账号的接口
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",describeAccountsModel.getAction());
        jsonParam.put("DBInstanceId",describeAccountsModel.getDBInstanceId());
//        jsonParam.put("Engine",describeAccountsModel.getEngine());
//        jsonParam.put("RegionId",describeAccountsModel.getRegionId());
        jsonParam.put("PageSize",describeAccountsModel.getPageSize());
        jsonParam.put("PageNumber",describeAccountsModel.getPageNumber());
        String uri = url+ ToSignUtil.getUrlNew("GET",jsonParam);
        System.out.println(uri);
       return given().urlEncodingEnabled(false).log().all()
                .when()
                .get(uri);
    }

    public Response CreateAccount( CreateAccountModel createAccountModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 15:00 2020/7/29
         * @ Param :[createAccountModel, accountName, password, accountType]
         * @ Return : io.restassured.response.Response
         * @ Description: 创建数据库账号
         */
        JSONObject jsonBody  = new JSONObject();
        jsonBody.put("AccountName",createAccountModel.getAccountName());
        jsonBody.put("AccountPassword",createAccountModel.getAccountPassword());
        jsonBody.put("AccountType",createAccountModel.getAccountType());
        JSONObject json = new JSONObject();
        json.put("Action",createAccountModel.getAction());
        json.put("DBInstanceId",createAccountModel.getDBInstanceId());
        String uri = url + ToSignUtil.getUrlNew("POST",json);
        System.out.println(uri);
       Response response =  given().urlEncodingEnabled(false).log().all()
                .contentType("application/json")
                .body(jsonBody.toJSONString())
                .when()
                .post(uri);
       return  response;
    }

//    @Test
    public void test() throws Exception {
        String accountName = "abc1234";
        String password = "Admin#123";
        String accountType = "normal";
        String instanceId = "mysql-j32b5vnidtkc";
//        CreateAccountModel createAccountModel = new CreateAccountModel();
//        createAccountModel.setAccountName(accountName);
//        createAccountModel.setAccountPassword(password);
//        createAccountModel.setAccountType(accountType);
//        createAccountModel.setDBInstanceId(instanceId);
//        CreateAccount(createAccountModel)
//                .then()
//                .log().all();
            DeleteAccountModel deleteAccountModel = new DeleteAccountModel();
            deleteAccountModel.setDBInstanceId(instanceId);
            deleteAccountModel.setAccountName(accountName);
            DeleteAccount(deleteAccountModel).then().log().all();
    }
}
