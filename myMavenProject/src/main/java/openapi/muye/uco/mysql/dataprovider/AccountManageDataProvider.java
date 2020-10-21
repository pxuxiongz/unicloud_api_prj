package openapi.muye.uco.mysql.dataprovider;

import openapi.muye.uco.util.CommonUtil;
import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 15:02 2020/7/29
 * @ Description: 账号管理的数据驱动
 */
public class AccountManageDataProvider {

    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }

    @DataProvider(name = "CreateAccount")
    public static Object[][] CreateAccount(){
        Object[][] o1 = new Object[][]{
                {"ab_123d","Admin#123","high"},
                {"a_123","ad!@#$%^&*()_+-=A123","normal"},
                {"a_12321213abdsfz","a_12321abdsfzad!@#$%^&*()_+-=A12","normal"}
        };


        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

    @DataProvider(name = "CreateAccountInvalid")
    public static Object[][] CreateAccountFail(){
        Object[][] o1 =  new Object[][]{
                {"","Admin#123","normal","AccountName.Invalid"},
                {"abc_!@#$%^&*()_+-=23a","ad!@#$%^&*()_+-=A123","normal","AccountName.Invalid"},
                {"中文","Admin#123","normal","AccountName.Invalid"},
                {"auto159590720115112","Admin@123","normal","AccountName.Invalid"},
                {"abc_1","Admin~123","normal","AccountPassword.Invalid"},
                {"a_1","Ad@123","normal","AccountPassword.Invalid"},
                {"a_1","A@123456","normal","AccountPassword.Invalid"},
                {"a_1","a@bcdefAb","normal","AccountPassword.Invalid"},
                {"a_3","a_12321abdsfzad!@#$%^&*()_+-=A123","normal","AccountPassword.Invalid"},
                {"a_3","","normal","AccountPassword.Invalid"},
                {"a_3","Admin#123","abc","AccountType.Invalid"},
                {"a_3","Admin#123","","AccountType.Invalid"},
                {"a_123","Admin#123","normal","AccountName.Exist"},
        };
        return CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider(name = "ResetAccount")
    public static Object[][] ResetAccount(){
        Object[][] o1 =  new Object[][]{
                {"Admin#123"},
                {"ad!@#$%^&*()_+-=A123"},
                {"a_12321abdsfzad!@#$%^&*()_+-=A12"}
        };
       return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider(name = "ResetAccountInvalid")
    public static Object[][] ResetAccountFail(){
        Object[][] o1 =  new Object[][]{
                {"a#123","AccountPassword.Invalid"},
                {"Adn#123","AccountPassword.Invalid"},
                {"ad!@#$%^&*()_+-=A1~23","AccountPassword.Invalid"},
                {"a_12321abdsfzad!@#$%^&*()_+-=A12123213","AccountPassword.Invalid"},
                {"","AccountPassword.Invalid"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

    @DataProvider(name = "GrantAccountPrivilege")
    public static Object[][] GrantAccountPrivilege(){
        Object[][] o1 =   new Object[][]{
                {"readWrite"},
                {"readOnly"},
                {"DB_Owner"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider(name = "RevokeAccountPrivilege")
    public static Object[][] RevokeAccountPrivilege(){
        Object[][] o1 =   new Object[][]{
                {"auto1"+CommonUtil.getTimeStamp(),"auto1"+CommonUtil.getTimeStamp(),"readWrite"},
                {"auto2"+CommonUtil.getTimeStamp(),"auto2"+CommonUtil.getTimeStamp(),"readOnly"},
                {"auto3"+CommonUtil.getTimeStamp(),"auto3"+CommonUtil.getTimeStamp(),"DB_Owner"}

        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

}
