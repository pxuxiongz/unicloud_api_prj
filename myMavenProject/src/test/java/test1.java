import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import static io.restassured.RestAssured.given;

public class test1 {
    @Test
    public void test(){
        String  s = "{\"msg\":\"success.\",\"res\":{\"subnetId\":\"d26501bd44044f7582ad274ac481fba9\",\"engineVersion\":\"3.6\",\"intranetAddress\":null,\"vpcInfo\":{\"instanceCode\":\"vpc.standard\",\"instanceId\":\"vpc-ji0sh3tqy32ob\",\"instanceName\":\"VPCYFB\",\"regionId\":\"cn-tianjin-yfb\",\"cidr\":\"172.16.0.0/16\",\"subnets\":[{\"name\":\"VPCYFBSUB\",\"allocationPool\":{\"start\":\"172.16.34.2\",\"end\":\"172.16.34.251\"},\"cidr\":\"172.16.34.0/24\",\"gatewayIp\":\"172.16.34.1\",\"id\":\"d26501bd44044f7582ad274ac481fba9\"}],\"userId\":\"30eb0df2-3341-4b59-be11-fa53fa915184\",\"status\":\"RUNNING\"},\"isNullWhitelist\":false,\"instanceName\":\"分片包年包月\",\"instanceType\":\"normal\",\"regionName\":\"天津研发-YFB区\",\"ip\":\"\",\"sourceInstanceId\":\"\",\"vipPortId\":null,\"endAt\":\"2020-09-18 00:00:00\",\"createAt\":\"2020-08-14 15:18:28\",\"specificationDetails\":[{\"specificationClassCode\":\"RDSMONGODB.Version\",\"componentCode\":\"RDSMONGODB.Version\",\"componentName\":\"RDSMONGODB版本\",\"specificationCode\":\"3.6\",\"specificationName\":\"3.6版本\"},{\"specificationClassCode\":\"mongodb.ee\",\"componentCode\":\"RDSMONGODB.Series\",\"componentName\":\"RDSMONGODB系列\",\"specificationCode\":\"EE\",\"specificationName\":\"分片集群\"},{\"specificationClassCode\":\"db.c\",\"componentCode\":\"RDSMONGODB.Instance\",\"componentName\":\"RDSMONGODB实例\",\"specificationCode\":\"db.c1.medium\",\"specificationName\":\"计算型2核4GB\"},{\"specificationClassCode\":\"db.s\",\"componentCode\":\"RDSMONGODB.Instance\",\"componentName\":\"RDSMONGODB实例\",\"specificationCode\":\"db.s1.large\",\"specificationName\":\"通用型2核8GB\"},{\"specificationClassCode\":\"ebs.local\",\"componentCode\":\"RDSMONGODB.DataDisk\",\"componentName\":\"RDSMONGODB云硬盘\",\"specificationCode\":\"ebs.local.ssd\",\"specificationName\":\"本地盘\"}],\"instanceId\":\"mongo-j386idvgxn6h\",\"payType\":\"YEAR_MONTH\",\"nodes\":[{\"dataDiskInstanceClassCode\":null,\"instanceCode\":\"db.s1.large\",\"memory\":8,\"dataDiskInstanceCode\":null,\"groupId\":\"mongo-j386idvgxn6h_mongos_group1\",\"cpu\":2,\"dataDiskStorageType\":null,\"vipPortId\":\"8cd6c717a662461697872fa15e103514\",\"nodeType\":\"mongos\",\"nodeList\":[{\"nodeRole\":\"\",\"nodeIp\":\"172.16.34.124\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_mongos_1\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"}],\"dataDiskSpecificationName\":null,\"nodeSpecificationName\":\"通用型2核8GB\",\"vip\":\"172.16.34.146:27017\",\"dataDisk\":null},{\"dataDiskInstanceClassCode\":\"ebs.local\",\"instanceCode\":\"db.c1.medium\",\"memory\":4,\"dataDiskInstanceCode\":\"ebs.local.ssd\",\"groupId\":\"mongo-j386idvgxn6h_configserver_group1\",\"cpu\":2,\"dataDiskStorageType\":\"local\",\"vipPortId\":null,\"nodeType\":\"configserver\",\"nodeList\":[{\"nodeRole\":\"primary\",\"nodeIp\":\"172.16.34.137\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_configserver_1\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"},{\"nodeRole\":\"secondary\",\"nodeIp\":\"172.16.34.139\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_configserver_2\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"},{\"nodeRole\":\"hidden\",\"nodeIp\":\"172.16.34.14\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_configserver_3\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"}],\"dataDiskSpecificationName\":\"本地盘\",\"nodeSpecificationName\":\"计算型2核4GB\",\"vip\":null,\"dataDisk\":20},{\"dataDiskInstanceClassCode\":\"ebs.local\",\"instanceCode\":\"db.c1.medium\",\"memory\":4,\"dataDiskInstanceCode\":\"ebs.local.ssd\",\"groupId\":\"mongo-j386idvgxn6h_shard_group2\",\"cpu\":2,\"dataDiskStorageType\":\"local\",\"vipPortId\":null,\"nodeType\":\"shard\",\"nodeList\":[{\"nodeRole\":\"primary\",\"nodeIp\":\"172.16.34.140\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_shard_4\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"},{\"nodeRole\":\"secondary\",\"nodeIp\":\"172.16.34.141\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_shard_5\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"},{\"nodeRole\":\"hidden\",\"nodeIp\":\"172.16.34.142\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_shard_6\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"}],\"dataDiskSpecificationName\":\"本地盘\",\"nodeSpecificationName\":\"计算型2核4GB\",\"vip\":null,\"dataDisk\":14},{\"dataDiskInstanceClassCode\":\"ebs.local\",\"instanceCode\":\"db.c1.medium\",\"memory\":4,\"dataDiskInstanceCode\":\"ebs.local.ssd\",\"groupId\":\"mongo-j386idvgxn6h_shard_group1\",\"cpu\":2,\"dataDiskStorageType\":\"local\",\"vipPortId\":null,\"nodeType\":\"shard\",\"nodeList\":[{\"nodeRole\":\"secondary\",\"nodeIp\":\"172.16.34.143\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_shard_1\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"},{\"nodeRole\":\"primary\",\"nodeIp\":\"172.16.34.144\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_shard_2\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"},{\"nodeRole\":\"hidden\",\"nodeIp\":\"172.16.34.145\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_shard_3\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"}],\"dataDiskSpecificationName\":\"本地盘\",\"nodeSpecificationName\":\"计算型2核4GB\",\"vip\":null,\"dataDisk\":14},{\"dataDiskInstanceClassCode\":null,\"instanceCode\":\"db.s1.large\",\"memory\":8,\"dataDiskInstanceCode\":null,\"groupId\":\"mongo-j386idvgxn6h_mongos_group2\",\"cpu\":2,\"dataDiskStorageType\":null,\"vipPortId\":\"5e9fd372d5d34793b0a705daba864a45\",\"nodeType\":\"mongos\",\"nodeList\":[{\"nodeRole\":\"primary\",\"nodeIp\":\"172.16.34.136\",\"azId\":\"cn-tianjin-yfb1\",\"nodeStatus\":\"running\",\"nodeId\":\"mongo-j386idvgxn6h_mongos_2\",\"nodePort\":\"27017\",\"nodeGroup\":\"\"}],\"dataDiskSpecificationName\":null,\"nodeSpecificationName\":\"通用型2核8GB\",\"vip\":\"172.16.34.147:27017\",\"dataDisk\":null}],\"instanceModeSpecificationName\":\"分片集群\",\"regionId\":\"cn-tianjin-yfb\",\"port\":27017,\"eipInfo\":null,\"vpcId\":\"vpc-ji0sh3tqy32ob\",\"instanceMode\":\"EE\",\"status\":\"running\"},\"code\":\"success\",\"auth\":true,\"status\":true}";
        JSONObject jsonObject = JSON.parseObject(s);
        String jsons = jsonObject.toJSONString();
        System.out.println(jsons);
        JSONArray jsonArray = JsonPath.read(jsons,"res.nodes.[?(@.dataDiskInstanceCode == \"ebs.local.ssd\")].groupId");
        System.out.println(jsonArray);

        Stack stk = new Stack();
        stk.add("a");
        stk.add("b");
        stk.push(1);
        stk.push(2);
        System.out.println(stk);
    }
@Test
    public void autotest() throws IOException {
    JSONArray jsonArray = JsonPath.read(new File("D:\\json.json"),"res.list[*].taskName");
    JSONArray jsonArray1 = JsonPath.read(new File("D:\\json.json"),"res.list[*].taskStatus");
    System.out.println(jsonArray);
    HashSet set = new HashSet(jsonArray);
    System.out.println(set);
    List list = new ArrayList();
    for(int i = 0;i < jsonArray1.size();i++){
        if(!list.contains(jsonArray1.get(i))){
            list.add(jsonArray1.get(i));
        }
    }
    System.out.println(list);
    }
    @Test
    public void testList(){
        List<String> l1 = new ArrayList();
        l1.add("abc");
        System.out.println(l1);

        com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();

        for (int i =0;i<=50;i++){
            jsonArray.add("abc"+i);;
        }

        System.out.println(jsonArray);
    }

}
