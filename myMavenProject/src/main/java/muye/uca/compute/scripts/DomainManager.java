package muye.uca.compute.scripts;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import  static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * @ Author :xx
 * @ Date : Created 15:57 2020/8/25
 * @ Description: 虚拟机操作
 */
public class DomainManager {

    @Test(dataProviderClass = muye.uca.compute.DataProvider.DomainProvider.class,dataProvider = "InstanceIdUserId")
    public void Test_DeleteVM(String instanceId,String userId) throws InterruptedException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("InstanceId",instanceId);
        jsonBody.put("UserId",userId);
        //先关闭虚拟机
        String url = "http://10.252.146.105:40201/uca/compute/v2.0/domain/shutdown";
        String requerID= "123"+Math.abs(new Random().nextInt());
        given().log().all()
                .body(jsonBody.toJSONString())
                .header("RequestId",requerID)
                .contentType("application/json")
                .when()
                .post(url)
                .then().log().all()
                .statusCode(200)
                .body("Status",equalTo("Success"))
                ;
        //删除虚拟机
        given().log().all()
                .body(jsonBody.toJSONString())
                .contentType("application/json")
                .when()
                .post("http://10.252.146.111:40202/uca/compute/v2.0/delivery/ucaDelivery/delete")
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
        String url = "http://10.252.146.105:40201/uca/compute/v2.0/domain/localdisk/destroy";
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
//    @Test
    public void Test_OMC(){
        String omcurl = "http://10.0.45.193:40299/api/omc/uca/center/v2.0/domain/list?current=1&size=100&";
        Response response = given().log().all()
                .header("X-Request-Region","cn-tianjin-yfb")
                .queryParam("domainState","running")
                .queryParam("domainTag","NOSQL")
//                .header("Referer","http://10.0.45.193:40299/")
//                .cookie("Hm_lvt_6ef5d3fc1524a3e2e9e085960813029b=1598152360,1598232354,1598316573,1598336186; Hm_lpvt_6ef5d3fc1524a3e2e9e085960813029b=1598354972")
                .when()
                .get(omcurl);
                response.then().log().all()
//        System.out.println(response.body().jsonPath().getString("Detail.Records[0].DomainState"));
                .statusCode(200);
        String lst = response.body().jsonPath().getString("Detail.Records.DomainName");
        System.out.println(lst);
        System.out.println(response.body().jsonPath().getString("Detail.Records.HostName"));
        System.out.println(response.body().jsonPath().getString("Detail.Records.UserId"));
    }
}
