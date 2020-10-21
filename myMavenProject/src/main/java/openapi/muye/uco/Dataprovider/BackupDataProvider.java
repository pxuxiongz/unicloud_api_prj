package openapi.muye.uco.Dataprovider;

import openapi.muye.uco.util.CommonUtil;
import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 14:02 2020/8/11
 * @ Description: 备份的数据驱动
 */
public class BackupDataProvider {

    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }


    @DataProvider(name = "ModifyBackupPolicy")
    public static Object[][] ModifyBackupPolicy(){
        Object[][] o1 = new Object[][]{
                {"Tuesday","00:00"},
                {"Monday,Tuesday,Sunday","07:50"},
                {"Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday","12:12"}
        };
        return CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
//    @Test
//    public void test(){
//        Object[][] o1 = new Object[][]{
//                {"Tuesday","00:00"},
//                {"Monday,Tuesday,Sunday","7:50"},
//                {"Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday","12:12"}
//        };
//        System.out.println(CommonUtil.arrayCombination(EngineModeInstance(),o1).toString());
//    }
    @DataProvider(name = "ModifyBackupPolicyFail")
    public static Object[][] ModifyBackupPolicy_Fail(){
        Object[][] o1 = new Object[][]{
                {"M","00:00","BackupPeriod校验失败","BackupPeriod.Invalid"},
                {"Monday","27:50","BackupTime校验失败","BackupTime.Invalid"},
                {"Monday","7:50","BackupTime校验失败","BackupTime.Invalid"},
                {"Monday","27:66","BackupTime校验失败","BackupTime.Invalid"},
                {"","12:12","BackupPeriod不能为空","BackupPeriod.NotNull"},
                {"Monday","","BackupTime校验失败","BackupTime.Invalid"}
        };
        return CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
}
