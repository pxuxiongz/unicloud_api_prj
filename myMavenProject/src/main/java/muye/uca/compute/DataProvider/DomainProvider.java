package muye.uca.compute.DataProvider;

import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 21:26 2020/8/25
 * @ Description: 计算相关
 */
public class DomainProvider {
    @DataProvider
    public static Object[][] InstanceIdUserId(){
        return new Object[][]{
//                {"ecs-BghQsjssQpJUyuwd68GJHT0aZTU4LZ04","920d682e-9e25-47f6-a66d-28dba2342ba5","sys-obEKc95qLQnpZzZ2FiPsK0h",6},
//                {"ecs-BghQsjssQpJUyuwd68GJHT0aZTU4LZ04","920d682e-9e25-47f6-a66d-28dba2342ba5","ebs-ltC407ULxaKVWVLYDZlCdUA",6},
//                {"ecs-jLqvvsgOsnPa5hYNbABwV9Bqrz6TjZdH","30eb0df2-3341-4b59-be11-fa53fa915184","sys-0ZnvQ4fLoxkLsKyaDxJWP86",6},

                {"ecs-Jo9nu5djNCZJOmxNZLkIkwXtpA0C4BIm","8f74d977-0208-4a7e-87ce-facacf798c76"},
                {"ecs-BbESDw4Rqz64exdgzeiR7WomNgIsYiON","30eb0df2-3341-4b59-be11-fa53fa915184"},
                {"ecs-j8T27N2VrsbCmfS7XlhPrMnAP0oHmtfv","d740bb85-4e44-4e6d-85f4-0cedb9ca8c33"},
                {"ecs-pQPmW64WxEqTQXUAiURmigwQtqLGncR8","d740bb85-4e44-4e6d-85f4-0cedb9ca8c33"},
                {"ecs-y4e7LgnAuE4M1eVEgEWFnthpZ8rsha0G","d740bb85-4e44-4e6d-85f4-0cedb9ca8c33"},
                {"ecs-x1ZxI5CxvcUutaTK5bCNud4HV6sJXknn","30eb0df2-3341-4b59-be11-fa53fa915184"},
                {"ecs-x2hPDumYMlho2h4vCKt3aBZDRPL4pHQY","30eb0df2-3341-4b59-be11-fa53fa915184"},
                {"ecs-pECIXHdCnCOA5Rl0w2oed4lIvm1INpQB","30eb0df2-3341-4b59-be11-fa53fa915184"},
                {"ecs-kwmqqttOvdQfbCG7fM046iwghDRsjWOt","d740bb85-4e44-4e6d-85f4-0cedb9ca8c33"},
                {"ecs-Jtg2qjjdzVqb7q9OYoWWMfIMKJyxwECj","2074e50a-8deb-4e77-b395-24140370b18c"}



        };
    }


    @DataProvider
    public static Object[][] DeleteDiskID(){
        return new Object[][]{
                {"sys-zuXhK40ODfOwFzaZgkZOzak",6},
                {"sys-Rs0qq1Kvd0O50MjYWggHaCd",6},
                {"sys-KmME7mOhOohDRLxDkBtRkbr",6},
                {"sys-A2keUlDSvhldnVMgBSjyNiw",6},
                {"ebs-y41a7lwvnsQynqXAYKJCkRJ",6},
                {"ebs-km9CPSNI04X7vQt45bWJueg",6},
                {"ebs-3FIjlJfffUND3qH9oEF6LH0",6},
                {"ebs-v8r3Ul66RXeXthewhgMfrXI",6},
                {"sys-3B0ipPzU4kIHJDJRVOL4i1q",6},
                {"sys-yHTHjKcdMBb73fu3o2fDhca",6},
                {"sys-IYMIKEy6PN64agSvoVQe2mX",6},
                {"ebs-vazAxGUoNnOc5fta6ivf9sR",6},
                {"ebs-v2HFnqDdff4Eu7c450LkjUX",6},
                {"ebs-CxUkosXrhf6tnaIIWN7Pxnb",6},
                {"sys-grbosQLY9mm32VgsUpFDRbQ",6},
                {"ebs-lv0lI2VWpGJTB7G2jqBvIWx",6}


        };
    }
}
