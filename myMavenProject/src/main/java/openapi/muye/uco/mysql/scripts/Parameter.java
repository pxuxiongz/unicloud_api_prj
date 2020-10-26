package openapi.muye.uco.mysql.scripts;

import com.alibaba.fastjson.JSONObject;
import openapi.muye.uco.common.model.GetDBInstanceModel;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.Parameter.DescribeParametersModel;
import openapi.muye.uco.mysql.models.Parameter.ModifyParameterModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class Parameter {
    String url = ConfigValue.url;
//    String instanceId = ConfigValue.getInstanceId();

    @Test(dataProvider = "EngineMode",dataProviderClass = openapi.muye.uco.mysql.dataprovider.CommonProvider.class)
    public void  Test_DescribeParameters(String Engine,String InstanceMode) throws Exception {
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
        DescribeParametersModel describeParametersModel = new DescribeParametersModel();
        describeParametersModel.setDBInstanceId(instanceId);
        DescribeParameters(describeParametersModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());

    }

    @Test(dataProvider = "ModifyParameter",dataProviderClass = openapi.muye.uco.mysql.dataprovider.ParameterDataProvider.class)
    public void  Test_ModifyParameter(String Engine,String InstanceMode,String paramName,String paramValue) throws Exception {
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
        ModifyParameterModel modifyParameterModel = new ModifyParameterModel();
        modifyParameterModel.setDBInstanceId(instanceId);
        modifyParameterModel.setParamName(paramName);
        modifyParameterModel.setParamValue(paramValue);
        ModifyParameter(modifyParameterModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("RequestId",notNullValue());

    }

    @Test(dataProvider = "ModifyParameterInvalid",dataProviderClass = openapi.muye.uco.mysql.dataprovider.ParameterDataProvider.class)
    public void  Test_ModifyParameterInvalid(String  Engine,String InstanceMode,String paramName,String paramValue) throws Exception {
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
        ModifyParameterModel modifyParameterModel = new ModifyParameterModel();
        modifyParameterModel.setDBInstanceId(instanceId);
        modifyParameterModel.setParamName(paramName);
        modifyParameterModel.setParamValue(paramValue);
        ModifyParameter(modifyParameterModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("ErrorCode",is("ParameterValue.Invalid"));

    }


    public Response  DescribeParameters(DescribeParametersModel describeParametersModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 14:24 2020/7/29
         * @ Param :[describeParametersModel]
         * @ Return : io.restassured.response.Response
         * @ Description: 查询参数列表
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",describeParametersModel.getAction());
        jsonParam.put("DBInstanceId",describeParametersModel.getDBInstanceId());
        String uri = url + ToSignUtil.getUrlNew("GET",jsonParam);

       return given().urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(uri);

    }

    public Response ModifyParameter(ModifyParameterModel modifyParameterModel) throws Exception {
        /**
         * @ Author :xx
         * @ Date : Created 14:08 2020/7/29
         * @ Param : * @param modifyParameterModel
         * @param paramName
         * @param paramValue
         * @ Description: 修改参数的方法
         */
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("Action",modifyParameterModel.getAction());
        jsonParam.put("DBInstanceId",modifyParameterModel.getDBInstanceId());
        JSONObject jsonBody = new JSONObject();
        JSONObject jsonParams = new JSONObject();
        jsonParams.put(modifyParameterModel.getParamName(),modifyParameterModel.getParamValue());
        jsonBody.put("Parameters",jsonParams.toJSONString());
        String uri = url + ToSignUtil.getUrlNew("PUT",jsonParam);
       return  given().urlEncodingEnabled(false)
               .body(jsonBody.toJSONString())
               .contentType("application/json")
                .log().all()
                .when()
                .put(uri);

    }

//    @Test
    public void  Test() throws Exception {
        DescribeParametersModel describeParametersModel = new DescribeParametersModel();
        describeParametersModel.setDBInstanceId("mysql-xyrgcbux7jy");
         Response response = DescribeParameters(describeParametersModel);
         response.then().log().all();
                response.then()
                .assertThat()
                .statusCode(200);
//                .body("Items[?(@.ParameterName == \"binlog_checksum\")].ParameterValue",is("none"));

    }

}
