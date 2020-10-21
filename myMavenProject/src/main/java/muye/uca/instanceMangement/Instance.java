package muye.uca.instanceMangement;
import muye.uca.base.BaseInfo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Instance {
    String baseInfo = BaseInfo.url;
    String createInstanceUrl = baseInfo+"/nosql/v1/delivery";
    private  String instanceId = "";
    String deleteInstanceUrl = baseInfo+"/nosql/v1/delivery";
    public  void setInstanceId(String instanceId){
        this.instanceId = instanceId;
    }
    public String getInstanceId(){
        return instanceId;
    }
    public  void createInstance(String instanceId){
        this.setInstanceId(instanceId);
        String body = "{\n" +
                "    \"DeliveryVersion\": \"Create.v1.0\",\n" +
                "    \"DeliveryId\": \"1224262728752570370\",\n" +
                "    \"ServiceType\": \"UNICLOUD::RDS\",\n" +
                "    \"Region\": \"cn-tianjin\",\n" +
                "    \"DeliveryUnits\": [\n" +
                "        {\n" +
                "            \"DeliveryUnitId\": \"1224262728752570371\",\n" +
                "            \"UserId\": \"30eb0df2-3341-4b59-be11-fa53fa915184\",\n" +
                "            \"Resources\": {\n" +
                "                \""+this.getInstanceId()+"\": {\n" +
                "                    \"ResourceType\": \"UNICLOUD::RDS\",\n" +
                "                    \"Properties\": {\n" +
                "                        \"EngineVersion\": \"3.2\",\n" +
                "                        \"Port\": \"6379\",\n" +
                "                        \"Engine\": \"redis\",\n" +
                "                        \"SourceInstance\": \"\",\n" +
                "                        \"InstanceMode\": \"SE\",\n" +
                "                        \"VPC\": \"vpc-jpc2j1yjs1xob\",\n" +
                "                        \"BackupId\": \"\",\n" +
                "                        \"Nodes\": [\n" +
                "                            {\n" +
                "                                \"Groups\": [\n" +
                "                                    {\n" +
                "                                        \"NodeIds\": [\n" +
                "                                            \""+this.getInstanceId()+"_node_1\"\n" +
                "                                        ],\n" +
                "                                        \"GroupId\": \""+this.getInstanceId()+"_group1\"\n" +
                "                                    }\n" +
                "                                ],\n" +
                "                                \"SecurityGroups\": [],\n" +
                "                                \"Type\": \"node\",\n" +
                "                                \"DataDisk\": {\n" +
                "                                    \"StorageType\": \"local\",\n" +
                "                                    \"Capacity\": \"10\",\n" +
                "                                    \"InstanceCode\": \"ebs.highIO.ssd\"\n" +
                "                                },\n" +
                "                                \"InstanceCode\": \"db.c1.medium\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"AccountPassword\": \"Admin#123\",\n" +
                "                        \"AvailableZones\": [\n" +
                "                            {\n" +
                "                                \"Type\": \"Master\",\n" +
                "                                \"Id\": \"cn-tianjin-a\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"InstanceName\": \""+this.getInstanceId()+"-mysqlname\",\n" +
                "                        \"Domain\": \"\",\n" +
                "                        \"Subnet\": \"22cd48dce7614d8a821c679778f213f4\",\n" +
                "                        \"InstanceType\": \"normal\",\n" +
                "                        \"MultiAZ\": false,\n" +
                "                        \"Tags\": [],\n" +
                "                        \"AccountName\": \"\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        given().log()
                .all()
                .contentType("application/json")
                .body(body)
                .when()
                .log()
                .all()
                .post(createInstanceUrl)
                .then()
                .assertThat()
                .equals("{\"code\":\"success\"}");

    }
    public  void getInstanceInfo(String instanceId){
        this.setInstanceId(instanceId);
        String getInstanceUrl = baseInfo+"/nosql/v1/instance/status?InstanceId="+this.getInstanceId();
        System.out.println(getInstanceUrl);
       given().contentType("application/json")
                .log()
                .all()
                .when()
                .get(getInstanceUrl)
                .then()
                .log()
                .all()
                .assertThat()
                .body("code",equalTo("success"))
                .body("data.list[0].Status",equalTo("running"));
    }
    public void deleteInstance(String instanceId){
        this.setInstanceId(instanceId);
        String body = "{\n" +
                "    \"DeliveryVersion\": \"Create.v1.0\",\n" +
                "    \"DeliveryId\": \"1224262728752570370\",\n" +
                "    \"ServiceType\": \"UNICLOUD::RDS\",\n" +
                "    \"Region\": \"cn-tianjin\",\n" +
                "    \"DeliveryUnits\": [\n" +
                "        {\n" +
                "            \"DeliveryUnitId\": \"1224262728752570371\",\n" +
                "            \"UserId\": \"30eb0df2-3341-4b59-be11-fa53fa915184\",\n" +
                "            \"Resources\": {\n" +
                "                \""+this.getInstanceId()+"\": {}\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        given().log()
                .all()
                .contentType("application/json")
                .body(body)
                .when()
                .delete(deleteInstanceUrl)
                .then()
                .body("code",equalTo("success"));

    }

    public static  void main(String[] args) {
        Boolean flag = true;
        int count = 0;
        while (flag) {
            Instance instance = new Instance();
            String instanceId = "redis-myautotest" + System.currentTimeMillis();
            System.out.println(instanceId);
            try {
                instance.createInstance(instanceId);
                Thread.sleep(250000);
                instance.getInstanceInfo(instanceId);
                Thread.sleep(5000);
                instance.deleteInstance(instanceId);
                count += 1;
                System.out.printf("恭喜你，这是第%d次成功执行创建删除实例了",count);
                Thread.sleep(10000);
            } catch (AssertionError e) {
                flag = false;
                e.printStackTrace();
                System.out.printf("这是第%d次执行了，Rest-assured匹配出错了，赶紧找开发定位问题！！！instanceId:"+instanceId,count);
            } catch (Exception h){
                flag = false;
                h.printStackTrace();
                System.out.printf("这是第%d次执行了，线程出错了，赶紧找开发定位问题！！！instanceId:"+instanceId,count);
            }

        }
    }

}
