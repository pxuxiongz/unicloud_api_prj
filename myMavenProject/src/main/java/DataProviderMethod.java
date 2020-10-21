import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DataProviderMethod {
    @DataProvider
    public static  Object[][] NoNameMethod(){
        return new Object[][]{
                {"DataWithNoName1"},
                {"DataWithNoName2"},
                {"DataWithNoName3"}
        };
    }
    @DataProvider(name="dataprovider1")
    public static Object[][] dataProvider1(){
        return new Object[][]{
                {"dataprovider1-1"},
                {"dataprovider1-2"}
        };
    }
    //指定名称2
//    @DataProvider(name="dataprovider2")
    public static Object[][] dataProvider2(){
        return new Object[][]{
                {"dataprovider2-1","dataprovider2-11"},
                {"dataprovider2-2","dataprovider2-2"}
        };
    }
//    @DataProvider(name = "dataprovider3")
    public static Object[][] dataProvider3(){
        return  new Object[][]{
                {"a","b"},
                {"a","c"}
        };
    }
    @DataProvider(name = "data")
    public static Object[][] data(){
        List<Object> result = Lists.newArrayList();
        result.addAll(Arrays.asList(dataProvider2()));
        result.addAll(Arrays.asList(dataProvider3()));
        return result.toArray(new Object[result.size()][]);
    }
    @Test
    public void test(){
        Object[][] o1 = new Object[][]{ {"dataprovider2-1","dataprovider2-11","dataprovider2-111"},
                {"dataprovider2-2","dataprovider2-22","dataprovider2-222"},{"dataprovider3-1","dataprovider3-2","dataprovider3-3"}};

        Object[][] o2 = new Object[][]{ {"a","b"},
                {"a","c"}};

//       System.arraycopy(o1,0,o2,0,o1.length);
//        System.out.println(o2.toString());
        System.out.println(o1[0][1]);
        System.out.println(o1.toString());
        Object[][] o3;
        for (int h = 0;h<o1.length;h++) {
            for (int i = 0; i < o2.length; i++) {
                for (int j = 0; j < o2[i].length; j++) {
                    o1[h][o1[h].length + j + 1] = o2[i][j];
                }
            }
        }
    }
    @Test
    public void test2(){
        Object[][] o2 = new Object[][]{ {"a","b"},
                {"a","c"}};
        o2[0][2] = "";
    }


    @Test
    public void test3() {
        Object[][] o1 = new Object[][]{
//                {"dataprovider2-1", "dataprovider2-11", "dataprovider2-111"},
//                {"dataprovider2-2", "dataprovider2-22", "dataprovider2-222"},
//                {"dataprovider3-1", "dataprovider3-2", "dataprovider3-3"}
                {"","Admin#123","normal","AccountName.NotNull"},
                {"abc_!@#$%^&*()_+-=23a","ad!@#$%^&*()_+-=A123","normal","AccountName.Invalid"},
                {"中文","Admin#123","normal","AccountName.Invalid"},
                {"auto159590720115112","Admin@123","normal","AccountName.Invalid"},
                {"abc_1","Admin~123","normal","AccountPassword.Invalid"},
                {"a_1","Ad@123","normal","AccountPassword.Invalid"},
                {"a_1","A@123456","normal","AccountPassword.Invalid"},
                {"a_1","a@bcdefAb","normal","AccountPassword.Invalid"},
                {"a_3","a_12321abdsfzad!@#$%^&*()_+-=A123","normal","AccountPassword.Invalid"},
                {"a_3","","normal","AccountPassword.NotNull"},
                {"a_3","Admin#123","abc","AccountType.Invalid"},
                {"a_3","Admin#123","","AccountType.NotNull"},
                {"a_123","Admin#123","normal","AccountName.Exist"}
        };

        Object[][] o2 = new Object[][]{{"mysql", "HA"},
                {"mysql", "SE"}};

        List<List<String>> doubleList = new ArrayList<>();

        for (int i = 0; i < o1.length; i++) {
            List<String> temp = new LinkedList<>();
            for (int j = 0; j < o1[i].length; j++) {
                temp.add((String) o1[i][j]);

            }
            for (int m = 0; m < o2.length; m++) {
                List<String> tempL = new LinkedList<>(temp);
                for (int n = 0; n < o2[m].length; n++) {
                    tempL.add((String) o2[m][n]);
                }
                doubleList.add(tempL);
            }


        }
        System.out.println(doubleList);

    }
}


