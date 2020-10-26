package openapi.muye.uco.config;

public class ConfigValue {
    //预发布193环境
    public static String url = "http://10.0.45.193:30990/api/rds?";
    public static String ak = "3tIbjx6Ekaa7Z2KZ";
    public static String aks = "D65WUTgsIocAYYtfY6TC7u1yVENSuE";
    public static String RegionId = "cn-tianjin-yfb";
    public static String ENV = "pre";
    public static String Engine  = "redis";  // rds、redis、mongodb
    //线上环境
    static{
        if(ENV.equals("pre")) {//测试环境
            url = "http://10.0.45.193:30990/api/"+Engine+"?";
            ak = "3tIbjx6Ekaa7Z2KZ";
            aks = "D65WUTgsIocAYYtfY6TC7u1yVENSuE";
            RegionId = "cn-tianjin-yfb";
        }
        if(ENV.equals("pro")){//线上环境:api.unicloud.com (103.252.251.25)
            url = "https://api.unicloud.com/api/"+Engine+"?";
            ak = "zEl1os171Wsc9L9L";
            aks = "RzsPEDLWQzsSaJhVdmoZZfw7G5jG6z";
            RegionId = "HB1-BJMY";
        }

    }
}
