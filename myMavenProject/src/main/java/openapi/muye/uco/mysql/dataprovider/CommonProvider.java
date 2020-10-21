package openapi.muye.uco.mysql.dataprovider;

import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 13:42 2020/7/30
 * @ Description: 通用的数据驱动
 */
public class CommonProvider {
    @DataProvider(name = "EngineMode")
    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }

}
