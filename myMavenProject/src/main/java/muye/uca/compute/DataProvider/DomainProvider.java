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

                {"ecs-yj2azLGOK845bklJMqooajCriZnPfFtl","30eb0df2-3341-4b59-be11-fa53fa915184","sys-tQPQ6sPK9BCeyaOZ9JU3Dlh",7},
                {"ecs-yj2azLGOK845bklJMqooajCriZnPfFtl","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-4loxINZwTrgamlbofAd8lOX",7},
                {"ecs-Yquxlj5msgUVykdVCJwTFFGBRbDjzbbf","920d682e-9e25-47f6-a66d-28dba2342ba5","sys-4OoOxVlNEYseI4yJN3ODW4A",5},
                {"ecs-Yquxlj5msgUVykdVCJwTFFGBRbDjzbbf","920d682e-9e25-47f6-a66d-28dba2342ba5","ebs-5nJB17Z6En48QQQMfYVbxRN",5},
                {"ecs-gpi7lwzZJ5IgIGeNgUoZyoMvwMwcaLr8","30eb0df2-3341-4b59-be11-fa53fa915184","sys-leFI0xxdCeDF1dfTZshQu40",6},
                {"ecs-gpi7lwzZJ5IgIGeNgUoZyoMvwMwcaLr8","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-lMaJxmP7foXVT2xnrz3JYrF",6},
                {"ecs-gpi7lwzZJ5IgIGeNgUoZyoMvwMwcaLr8","30eb0df2-3341-4b59-be11-fa53fa915184","sys-leFI0xxdCeDF1dfTZshQu40",5},
                {"ecs-gpi7lwzZJ5IgIGeNgUoZyoMvwMwcaLr8","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-lMaJxmP7foXVT2xnrz3JYrF",5},
                {"ecs-kBi6mS8H0fCEl7AHWeynAYO7mpS2wNNr","30eb0df2-3341-4b59-be11-fa53fa915184","sys-3ErKXn6ugOVaivr9Ct4CcaD",6},
                {"ecs-kBi6mS8H0fCEl7AHWeynAYO7mpS2wNNr","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-ncs0g3gcaCDDR7dBx0HjUQJ",6},
                {"ecs-kBi6mS8H0fCEl7AHWeynAYO7mpS2wNNr","30eb0df2-3341-4b59-be11-fa53fa915184","sys-3ErKXn6ugOVaivr9Ct4CcaD",5},
                {"ecs-kBi6mS8H0fCEl7AHWeynAYO7mpS2wNNr","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-ncs0g3gcaCDDR7dBx0HjUQJ",5},
                {"ecs-BxPUFsEyOukPcCWHuihtRpFxOSTrO5m2","30eb0df2-3341-4b59-be11-fa53fa915184","sys-BLFmEyqc0Z67Q5u1QVhIDv9",5},
                {"ecs-BxPUFsEyOukPcCWHuihtRpFxOSTrO5m2","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-SYpOTEOVPhTTEpffhXkFDiC",5},
                {"ecs-2TW1ukVrQHK7ZQ48aJtOWc9pv84loyX1","30eb0df2-3341-4b59-be11-fa53fa915184","sys-vbPy4Z9TJuYK7rAKiH1ZaMn",6},
                {"ecs-2TW1ukVrQHK7ZQ48aJtOWc9pv84loyX1","30eb0df2-3341-4b59-be11-fa53fa915184","ebs-v45ThtEVtZjueS5yeX79UTG",6},
                {"ecs-4hUMITcCBUpYl1uN0nSfG3yfJqLKr8YR","30eb0df2-3341-4b59-be11-fa53fa915184","sys-sVFiDfYxAfAIH2BqqR1ULRY",6},
                {"ecs-4hUMITcCBUpYl1uN0nSfG3yfJqLKr8YR","30eb0df2-3341-4b59-be11-fa53fa915184","sys-sVFiDfYxAfAIH2BqqR1ULRY",5}



        };
    }


    @DataProvider
    public static Object[][] DeleteDiskID(){
        return new Object[][]{
                {"ebs-1T9P5tv22JhOFE5rcu2By9y",6},
                {"ebs-4qsGAVTLU6G4sydErIpgmqc",6},
                {"ebs-c1jQmGYV9cwJ4pPWLEMaFor",6},
                {"ebs-KyYwxixwtb3sWMYBUuZbIzn",6},
                {"ebs-llrOTNAEeMGVFGwM9KgF40n",6},
                {"ebs-wHKa1N3pCqLK5LobnS19uQ1",6},
                {"sys-2PiVtUJLFnH01EiImj3BHmg",6},
                {"sys-7zgDstQEEkfSr5u1GxikpJd",6},
                {"sys-BNBJo5KKlJo0YoP84ZWB1qg",6},
                {"sys-R7cjSo8bbsZHpFANDGa5oOF",6},
                {"sys-uqqA21KQfFyqoCxOBB9fEsK",6},
                {"sys-vfZ12tbeRuBiBCibnghTfwr",6}
        };
    }
}
