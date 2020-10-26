package openapi.muye.uco.redis.dataprovider;

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
                {"SE",3.2},
                {"HA",3.2},
                {"EE",3.2},
                {"SE",6.0},
                {"HA",6.0}
        };
    }

}
