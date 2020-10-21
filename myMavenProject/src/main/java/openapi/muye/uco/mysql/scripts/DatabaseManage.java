package openapi.muye.uco.mysql.scripts;

import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.DBManage.CreateDatabaseModel;
import openapi.muye.uco.mysql.models.DBManage.DeleteDatabaseModel;
import openapi.muye.uco.mysql.models.DBManage.DescribeDatabasesModel;
import openapi.muye.uco.mysql.models.InstanceManage.GetDBInstanceModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.*;

import static io.restassured.RestAssured.given;

public class DatabaseManage {
    String url = ConfigValue.url;
//    String instanceId = ConfigValue.getInstanceId();

    @Test (dataProviderClass = openapi.muye.uco.mysql.dataprovider.DatabaseManageDataProvider.class,dataProvider = "CreateDatabase")
    public void Test_CreateDatabase(String Engine,String InstanceMode,String dbName,String characterSetName) throws Exception {
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
        CreateDatabaseModel createDatabaseModel = new CreateDatabaseModel();
        createDatabaseModel.setDBName(dbName);
        createDatabaseModel.setCharacterSetName(characterSetName);
        createDatabaseModel.setDBInstanceId(instanceId);
        CreateDatabase(createDatabaseModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }
    @Test (dataProviderClass = openapi.muye.uco.mysql.dataprovider.DatabaseManageDataProvider.class,dataProvider = "CreateDatabaseInvalid")
    public void Test_CreateDatabaseInvalid(String Engine,String InstanceMode,String dbName,String characterSetName,String ErrorCode) throws Exception {
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
        CreateDatabaseModel createDatabaseModel = new CreateDatabaseModel();
        createDatabaseModel.setDBName(dbName);
        createDatabaseModel.setCharacterSetName(characterSetName);
        createDatabaseModel.setDBInstanceId(instanceId);
        CreateDatabase(createDatabaseModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode",is(ErrorCode));
    }
//    @Test
    public void Test_DescribeDatabases() throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 17:09 2020/7/29
         * @ Param :[]
         * @ Return : void
         * @ Description: 其他地方多次用到查询，不再单独测试
         */
        String InstanceMode = "SE";
        String Engine = "mysql";
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        DescribeDatabasesModel describeDatabasesModel = new DescribeDatabasesModel();
        describeDatabasesModel.setDBInstanceId(instanceId);
        DescribeDatabases(describeDatabasesModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }

//    @Test()
    public void Test_DeleteDatabase(String dbName) throws Exception {
        DeleteDatabaseModel deleteDatabaseModel = new DeleteDatabaseModel();
        deleteDatabaseModel.setDBName(dbName);
        DeleteDatabase(deleteDatabaseModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());
    }

    @Test(dataProvider = "DeleteDatabaseFail",dataProviderClass = openapi.muye.uco.mysql.dataprovider.DatabaseManageDataProvider.class)
    public void Test_DeleteDatabaseFail(String Engine,String InstanceMode,String dbName) throws Exception {
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
        DeleteDatabaseModel deleteDatabaseModel = new DeleteDatabaseModel();
        deleteDatabaseModel.setDBName(dbName);
        deleteDatabaseModel.setDBInstanceId(instanceId);
        DeleteDatabase(deleteDatabaseModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("RequestId",notNullValue());
    }
    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class,priority = 100)
    public void Test_ClearDatabase(String Engine,String InstanceMode) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 17:21 2020/7/29
         * @ Param :[]
         * @ Return : void
         * @ Description: 清理数据库
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
        while(true){
            DescribeDatabasesModel describeDatabasesModel = new DescribeDatabasesModel();
            describeDatabasesModel.setDBInstanceId(instanceId);
            Response response = DescribeDatabases(describeDatabasesModel);
            response.then().log().all()
                    .assertThat()
                    .statusCode(200);
            int count = response.getBody().jsonPath().getInt("TotalRecordCount");
            if (count == 0){
                return;
            }
            String dbName = response.getBody().jsonPath().getString("Items[0].DBName");
            DeleteDatabaseModel deleteDatabaseModel = new DeleteDatabaseModel();
            deleteDatabaseModel.setDBInstanceId(instanceId);
            deleteDatabaseModel.setDBName(dbName);
            DeleteDatabase(deleteDatabaseModel).then().log().all().
                                                        assertThat().
                                                        statusCode(200);

        }
    }

    public Response CreateDatabase(CreateDatabaseModel createDatabaseModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 17:04 2020/7/29
         * @ Param :[createDatabaseModel, dbName, characterSetName]
         * @ Return : io.restassured.response.Response
         * @ Description: 创建数据库
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",createDatabaseModel.getAction());
        jsonParam.put("DBInstanceId",createDatabaseModel.getDBInstanceId());

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("DBName",createDatabaseModel.getDBName());
        jsonBody.put("CharacterSetName",createDatabaseModel.getCharacterSetName());

        String uri = url + ToSignUtil.getUrlNew("POST",jsonParam);

        return given().urlEncodingEnabled(false)
                .log().all()
                .body(jsonBody.toJSONString())
                .contentType("application/json")
                .when()
                .post(uri);
    }
    public Response DescribeDatabases(DescribeDatabasesModel describeDatabasesModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 17:05 2020/7/29
         * @ Param :[]
         * @ Return : io.restassured.response.Response
         * @ Description: 查询数据库
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",describeDatabasesModel.getAction());
        jsonParam.put("DBInstanceId",describeDatabasesModel.getDBInstanceId());
        jsonParam.put("PageSize",describeDatabasesModel.getPageSize());
        jsonParam.put("PageNumber",describeDatabasesModel.getPageNumber());
        String uri = url + ToSignUtil.getUrlNew("GET",jsonParam);
        return given().urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(uri);
    }
    public Response DeleteDatabase(DeleteDatabaseModel deleteDatabaseModel ) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 17:06 2020/7/29
         * @ Param :[deleteDatabaseModel, dbName]
         * @ Return : io.restassured.response.Response
         * @ Description: 删除数据库
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",deleteDatabaseModel.getAction());
        jsonParam.put("DBInstanceId",deleteDatabaseModel.getDBInstanceId());
        jsonParam.put("DBName",deleteDatabaseModel.getDBName());

        String uri = url + ToSignUtil.getUrlNew("DELETE",jsonParam);

        return given().urlEncodingEnabled(false)
                .log().all()
                .when()
                .delete(uri);
    }


//    @Test
    public void test() throws Exception {
        String instanceId = "mysql-j32b5vnidtkc";
        String dbName = "test123";
        String characterSetName = "utf8";
        CreateDatabaseModel createDatabaseModel = new CreateDatabaseModel();
        createDatabaseModel.setDBName(dbName);
        createDatabaseModel.setCharacterSetName(characterSetName);
        createDatabaseModel.setDBInstanceId(instanceId);
        CreateDatabase(createDatabaseModel)
                .then()
                .log().all();
//                .assertThat()
//                .statusCode(400)
//                .body("ErrorCode",is(ErrorCode));
    }

}
