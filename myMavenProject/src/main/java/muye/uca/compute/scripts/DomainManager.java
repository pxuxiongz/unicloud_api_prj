package muye.uca.compute.scripts;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.springframework.core.ReactiveAdapterRegistry;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import  static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * @ Author :xx
 * @ Date : Created 15:57 2020/8/25
 * @ Description: 虚拟机操作
 */
public class DomainManager {
    String UCAURL = "http://10.252.146.105"; //YFB
//    String UCAURL = "http://10.254.161.107";//CTO

    @Test(dataProviderClass = muye.uca.compute.DataProvider.DomainProvider.class,dataProvider = "InstanceIdUserId")
    public void Test_DeleteVM(String instanceId,String userId) throws InterruptedException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("InstanceId",instanceId);
        jsonBody.put("UserId",userId);
        //先关闭虚拟机
        String url = UCAURL+":40201/uca/compute/v2.0/domain/shutdown";
        String requerID= "123"+Math.abs(new Random().nextInt());
        //查询虚机状态
        Map<String,String> params = new HashMap<>();
        params.put("instanceId",instanceId);
        params.put("userId",userId);
//        boolean flag = true;
        int time = 1;
        while (true){
            given().log().all()
                    .body(jsonBody.toJSONString())
                    .header("RequestId",requerID)
                    .contentType("application/json")
                    .when()
                    .post(url)
                    .then().log().all()
//                    .statusCode(200)
//                    .body("Status",equalTo("Success"))
            ;

            Response VmStatusResponse = given().log().all()
                    .queryParams(params)
                    .when()
                    .get(UCAURL+":40201/uca/compute/v2.0/domain/describeStatus")
                    ;
            VmStatusResponse.then().log().all();
            System.out.printf("循环执行测试是%d",time);
            if (VmStatusResponse.body().jsonPath().getString("Detail").equals("stop")
             || VmStatusResponse.body().jsonPath().getString("Detail").equals("deleted")
             || VmStatusResponse.body().jsonPath().getString("Detail").isEmpty()){
               break;
            }
            Thread.sleep(5000);
            time += 1;

            if (time > 10){
                break;
            }

        }

        //删除虚拟机
        given().log().all()
                .body(jsonBody.toJSONString())
                .contentType("application/json")
                .when()
                .post(UCAURL+":40202/uca/compute/v2.0/delivery/ucaDelivery/delete")
                .then().log().all()
                .statusCode(200)
                .body("Status",equalTo("Success"))
        ;
        Thread.sleep(1000);

    }

    @Test(dataProvider = "DeleteDiskID",dataProviderClass = muye.uca.compute.DataProvider.DomainProvider.class)
    public void Test_deleteDisk(String diskId,int id){
        /**
         * @ Author :xx
         * @ Date : Created 11:20 2020/8/26
         * @ Param :[diskId, id]
         * @ Return : void
         * @ Description: 删除磁盘
         */
//        String url = "http://10.252.146.105:40201/uca/compute/v2.0/domain/localdisk/destroy";//YFB环境
        String url = UCAURL+":40201/uca/compute/v2.0/domain/localdisk/destroy";//CTO环境
//        String diskId = "sys-aKeb2h8nMSvjcUZidOhPZ1U";
        String hostId="";
        String requerID= "123"+Math.abs(new Random().nextInt());
        System.out.println(requerID);
        if(id==5){
            hostId="5B256914-346B-03D3-E611-B6CF92EB65DA";
        }
        if(id == 6){
            hostId = "5B25691A-346B-03CD-E611-B6CF4EBC08E2";
        }
        if(id == 7){
            hostId = "5B2569FE-346B-03D2-E611-B7CF52B37141";
        }
        if(id == 3){
            hostId = "E97A7B52-CFB6-11E6-03CD-7485C4BD7096";
        }
        if(id == 4){
            hostId = "BFE7A26C-CFB9-11E6-03CC-7485C4BD7066";
        }
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("DiskId",diskId);
        jsonBody.put("HostId",hostId);
        given().log().all()
                .body(jsonBody.toJSONString())
                .header("RequestId",requerID)
                .contentType("application/json")
                .when()
                .post(url)
                .then().log().all()
                .statusCode(200)
                .body("Status",equalTo("Success"))
                .body("Message",equalTo("Destroy domain lvm successfully"))
                .body("Detail",equalTo(true))
                .body("Sucess",equalTo(true));
    }

//    @Test
    public void DomainDestroy(){
        String instanceId = "ecs-A7iQOda49aXYMvdjz0o46xNyHzMy2Tfl";
        String userId="7e922a06-577c-414a-8049-e878f2b1fea3";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("InstanceId",instanceId);
        jsonBody.put("UserId",userId);
        given().log().all()
                .body(jsonBody.toJSONString())
                .contentType("application/json")
                .when()
                .post("http://10.252.146.111:40202/uca/compute/v2.0/delivery/ucaDelivery/delete")
                .then().log().all()
                .statusCode(200)
                .body("Status",equalTo("Success"));

    }
//@Test
    public void DomainShutdown(){
        String url = "http://10.252.146.105:40201/uca/compute/v2.0/domain/shutdown";
        String instanceId = "ecs-A7iQOda49aXYMvdjz0o46xNyHzMy2Tfl";
        String userId="7e922a06-577c-414a-8049-e878f2b1fea3";
        String requerID= "123"+Math.abs(new Random().nextInt());
        System.out.println(requerID);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("InstanceId",instanceId);
        jsonBody.put("UserId",userId);
        given().log().all()
                .body(jsonBody.toJSONString())
                .header("RequestId",requerID)
                .contentType("application/json")
                .when()
                .post(url)
                .then().log().all()
                .statusCode(200)
                .body("Status",equalTo("Success"));
    }
    @Test
    public void Test_OMC(){
        //查询虚机状态
        String instanceId = "ecs-0foI0DxctquFZQNBJ19yyQijaHQzTN5m";
        String userId = "30eb0df2-3341-4b59-be11-fa53fa915184";
        Map<String,String> params = new HashMap<>();
        params.put("instanceId",instanceId);
        params.put("userId",userId);
        Response VmStatusResponse = given().log().all()
                .queryParams(params)
                .when()
                .get(UCAURL+":40201/uca/compute/v2.0/domain/describeStatus")
                ;
        if (VmStatusResponse.body().jsonPath().getString("Detail").equals("stop")){
            System.out.println("ok");
        }
//        String lst = response.body().jsonPath().getString("Detail.Records.DomainName");
//        System.out.println(lst);
//        System.out.println(response.body().jsonPath().getString("Detail.Records.HostName"));
//        System.out.println(response.body().jsonPath().getString("Detail.Records.UserId"));
    }
}
