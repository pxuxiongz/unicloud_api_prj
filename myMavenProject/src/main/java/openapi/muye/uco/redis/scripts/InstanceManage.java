package openapi.muye.uco.redis;

import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import openapi.muye.uco.config.ConfigValue;
import openapi.muye.uco.mysql.models.InstanceManage.DeleteDBInstanceModel;
import openapi.muye.uco.mysql.models.InstanceManage.GetDBInstanceModel;
import openapi.muye.uco.util.ToSignUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @ Author :xx
 * @ Date : Created 17:35 2020/9/9
 * @ Description: redis实例管理
 */
public class InstanceManage {

    static String url = ConfigValue.url;
    static String RegionId = ConfigValue.RegionId;
    public Response DeleteDBInstance(DeleteDBInstanceModel deleteDBInstanceModel) throws Exception {
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
    @Test
    public void Test_DeleteInstance() throws Exception {
        String InstanceMode = "SE";
        String Engine = "redis";
        GetDBInstanceModel getDBInstanceModel = new GetDBInstanceModel();
        getDBInstanceModel.setInstanceMode(InstanceMode);
        getDBInstanceModel.setEngine(Engine);
        getDBInstanceModel.setInstanceType("normal");
//        String instanceId = CreateInstance.getInstanceIdCommon(getDBInstanceModel);
        String instanceId = "redis-j6t5rawgwict";
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
}
