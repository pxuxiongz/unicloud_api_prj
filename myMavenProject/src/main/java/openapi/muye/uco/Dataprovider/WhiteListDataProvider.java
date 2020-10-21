package openapi.muye.uco.Dataprovider;

import openapi.muye.uco.util.CommonUtil;
import org.testng.annotations.DataProvider;

public class WhiteListDataProvider {
    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }

    @DataProvider (name = "AddWhiteListOK")
    public static Object[][] AddWhiteList(){
        Object[][] o1 =  new Object[][] {
                {"a1","0.0.0.0,0.0.0.0/0"},
                {"a_b","192.168.1.1"},
                {"a_b123","223.255.255.255"},
                {"abcdefghijklmnopqrstuvwxyz_12345","1.1.1.1,10.0.0.0/8,172.16.0.0/16,192.168.10.0/24,255.255.255.255"}

        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

    @DataProvider(name = "AddWhiteListGroupNameInvalid")
    public static Object[][] AddWhiteListFail1(){
        Object[][] o1 = new Object[][]{
                {"a","1.1.1.1"},
                {"A_b1","8.8.8.8"},
                {"1a","100.1.1.1"},
                {"abcdefghijklmnopqrstuvwxyz_1234567","1.2.3.4"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider(name = "AddWhiteListIpInvalid")
    public static Object[][] AddWhiteListFail2(){
        Object[][] o1 = new Object[][]{
                {"abc","0","IP.Format.Invalid"},
                {"z1","192.168.1.256","IP.Format.Invalid"},
                {"abc_1","256.1.1.1","IP.Format.Invalid"},
                {"a123","1.1.1.1.1","IP.Format.Invalid"},
                {"a_1","223.1.1.1/-1","IP.Format.Invalid"},
                {"a_1","255.1.1.1/33","IP.Format.Invalid"},
                {"a_1","abc","IP.Format.Invalid"},
                {"a_1","1.1.1.1;2.1.1.1","IP.Format.Invalid"},
                {"a_1","1.1.1.1,1.1.1.1","IP.Repeated"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

    @DataProvider(name = "AddWhiteListIpNumInvalid")
    public static Object[][] AddWhiteListFail3(){
        Object[][] o1 = new Object[][]{
                {"a_1","1.1.1.1,144.254.180.254/32,144.254.181.254/32,144.254.182.254/32,144.254.183.254/32,144.254.184.254/32,144.254.185.254/32,144.254.186.254/32,144.254.187.254/32,144.254.188.254/32,144.254.189.254/32,244.134.110.254/32,244.134.111.254/32,244.134.112.254/32,244.134.113.254/32,244.134.114.254/32,244.134.115.254/32,244.134.116.254/32,244.134.117.254/32,244.134.118.254/32,244.134.119.254/32"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

    @DataProvider(name = "AddWhiteListNull")
    public static Object[][] AddWhiteListFail4(){
        Object[][] o1 = new Object[][]{
                {"a_1","","IPList不能为空","IPList.NotNull"},
                {"","1.1.1.1","GroupName校验失败，由小写字母、数字、下划线组成，以小写字母开头，以字母或数字结尾。长度为2-32个字符","GroupName.Invalid"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }

}
