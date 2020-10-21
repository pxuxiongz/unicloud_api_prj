package openapi.muye.uco.Dataprovider;

import openapi.muye.uco.util.CommonUtil;
import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 17:22 2020/7/29
 * @ Description: 数据库的数据驱动
 */
public class DatabaseManageDataProvider {
    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }
    @DataProvider (name = "CreateDatabase")
    public static Object[][] CreateDatabase(){
        Object[][] o1 = new Object[][]{
                {"z_b123","utf8"},
                {"Z","gbk"},
                {"z1","gbk"},
                {"auto_1","utf8mb4"},
                {"auto_2","gb2312"},
                {"abc1234567890_defghijklmnopqrstuvwxyzabc1234567890_defghijklmnop","utf32"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider (name = "CreateDatabaseInvalid")
    public static Object[][] CreateDatabaseInvalid(){
        Object[][] o1 =  new Object[][]{
                {"112","utf8","DBName.Invalid"},
                {"_","utf8","DBName.Invalid"},
                {"中文","gbk","DBName.Invalid"},
                {"abc1234567890_defghijklmnopqrstuvwxyzabc1234567890_defghijklmnopq","utf8mb4","DBName.Invalid"},
                {"","gb2312","DBName.Invalid"},
                {"z","gb2312123","RequestParam.Invalid"},
                {"z","","CharacterSetName.NotNull"},
                {"z_b123","utf8","RequestParam.Invalid"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider (name = "DeleteDatabaseFail")
    public static Object[][] DeleteDatabaseFail(){
        Object[][] o1 = new Object[][]{
                {"aaaaaaa"},

        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
}
