package openapi.muye.uco.redis.dataprovider;

import openapi.muye.uco.config.ConfigValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @ Author :xx
 * @ Date : Created 22:06 2020/7/29
 * @ Description: 创建实例的数据驱动
 */
public class CreateInstanceProvider {
    public static String Engine = "redis";
    public static String RegionId = ConfigValue.RegionId;
    public static int Port = 6379;
    public static String EngineVersion = "6.0";
    public static String AzId = "cn-tianjin-a-cto";
    public static String VpcId = "vpc-rxtptt5yxl2ob";
    public static String VpcSubnetId = "bdd539457b004f8caadc44270aed0cf5";
    public static String InstanceMode = "SE";
    public static String InstanceType = "normal";
    static{
        if(RegionId.equals("cn-tianjin-cto")){
               AzId = "cn-tianjin-a-cto";
               VpcId = "vpc-rxtptt5yxl2ob";
               VpcSubnetId = "bdd539457b004f8caadc44270aed0cf5";
        }
        if(RegionId.equals("cn-tianjin-yfb")){
            AzId = "cn-tianjin-yfb1";
            VpcId = "vpc-ji0sh3tqy32ob";
            VpcSubnetId = "d26501bd44044f7582ad274ac481fba9";
        }
        if(RegionId.equals("HB1-BJMY")){
            AzId = "HB1-BJMY1";
            VpcId = "vpc-tg0dr0uza5sob";
            VpcSubnetId = "0530bc60050c4a7db6b51384f0823483";
        }

    }
    @DataProvider(name = "CreateInstance")
    public static Object[][] CreateInstance(){
        return  new Object[][]{
//                {Engine,RegionId,Port,"3.2",AzId,VpcId,InstanceMode,InstanceType,VpcSubnetId,"YEAR_MONTH",1,"month","manualrenew","","Admin#123"},
//                {Engine,RegionId,Port,"6.0",AzId,VpcId,InstanceMode,InstanceType,VpcSubnetId,"YEAR_MONTH",1,"month","manualrenew","","Admin#123"},
//                 {Engine,RegionId,Port,"3.2",AzId,VpcId,"HA",InstanceType,VpcSubnetId,"DAY_MONTH",1,"month","manualrenew",AzId,"Admin#123"},
//                {Engine,RegionId,Port,"6.0",AzId,VpcId,"HA",InstanceType,VpcSubnetId,"DAY_MONTH",1,"month","manualrenew",AzId,"Admin#123"},
                 {Engine,RegionId,Port,"3.2",AzId,VpcId,"EE",InstanceType,VpcSubnetId,"DAY_MONTH",1,"month","manualrenew",AzId,"Admin#123"}
        };
    }


    @DataProvider(name = "CreateInstanceInvalid")
    public static Object[][] CreateInstanceInvalid(){
        return  new Object[][]{
                {"abc",RegionId,Port,EngineVersion,AzId,VpcId,InstanceMode,InstanceType,VpcSubnetId,"YEAR_MONTH",1,"month","manualrenew","",""},
                {Engine,RegionId,Port,EngineVersion,AzId,VpcId,"HA",InstanceType,VpcSubnetId,"CHARGING_HOURS",1,"month","manualrenew",AzId,""},
                {Engine,RegionId,Port,EngineVersion,AzId,VpcId,InstanceMode,InstanceType,VpcSubnetId,"DAY_MONTH",1,"month","manualrenew","",""}
        };
    }


}
