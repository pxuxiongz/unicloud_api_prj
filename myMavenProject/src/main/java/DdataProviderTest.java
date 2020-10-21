import openapi.muye.uco.config.ConfigValue;
import org.testng.annotations.*;
import org.testng.internal.annotations.IDataProvidable;
import sun.security.krb5.Config;

@Test(dataProvider ="dataprovider2",dataProviderClass=DataProviderMethod.class)
public class DdataProviderTest {
    @Test(dataProvider="dataprovider1",dataProviderClass=DataProviderMethod.class)
    public void doTestNG(String testdatas) {
        System.out.println("未指定名称，数据源名为方法名NoNameMethod:"+testdatas);
//        System.out.println("实例ID是:"+instanceId);
    }

//    @Test(dataProvider="dataprovider2",dataProviderClass=DataProviderMethod.class)
//    public void test1(String data){
//        tm.setInstanceId(data);
//    }
//
    @Test(dataProvider="data",dataProviderClass=DataProviderMethod.class)
    public void test2(String a,String b) throws Exception {
        System.out.println("-----:"+a);
        System.out.println("-----:"+b);

    }
}
