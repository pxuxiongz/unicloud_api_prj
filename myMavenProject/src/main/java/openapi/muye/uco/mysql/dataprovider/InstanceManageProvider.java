package openapi.muye.uco.mysql.dataprovider;

import openapi.muye.uco.util.CommonUtil;
import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 10:56 2020/7/30
 * @ Description: 实例管理的数据驱动
 */
public class InstanceManageProvider {
    public static String RegionId = "cn-tianjin-cto";

    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }
    @DataProvider(name = "RenewDBInstance")
    public static Object[][] RenewDBInstance(){
        Object[][] o1 = new Object[][]{
                {1,"month"},
                {12,"month"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

    @DataProvider(name = "RenewDBInstanceInvalid")
    public static Object[][] RenewDBInstanceInvalid(){
        Object[][] o1 = new Object[][]{
                {13,"month","UsedTime.Invalid"},
                {1,"year","Period.Invalid"},
                {0,"month","UsedTime.Invalid"},
                {1,"","Period.NotNull"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
//    @DataProvider(name = "DeleteDBInstance")
//    public static Object[][] DeleteDBInstance(){
//        Object[][] o1 =  new Object[][]{
//                {RegionId,"SE"},
//                {RegionId,"HA"}
//        };
//        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
//    }
}
