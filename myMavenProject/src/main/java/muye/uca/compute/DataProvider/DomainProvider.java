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
                {"ebs-9YAl3qkYTtYIlpjkX6kiWsP",7},
                {"ebs-dGLildSPFCATH6tc8nd12EN",7},
                {"ebs-dsPFiCkU4FBXWJn1bIv0Azf",6},
                {"ebs-f3mVTESdcl2B0WDAzVLCUjo",7},
                {"ebs-FpiotelHZJCHtFDde2Bud3l",7},
                {"ebs-gwLk9ARYh0maS8AW4qdHG7h",7},
                {"ebs-I7ZjKK2fCWCkdgu04t0hsYF",5},
                {"ebs-IrXbSFXRj8E8Z6ejaqCTdt6",6},
                {"ebs-Okdd5tP6ddVeViB0ekeH5lz",6},
                {"ebs-RhpMFmolr4XXek7eaH0whTD",6},
                {"ebs-UwKhfiM2slfqBJSDN9brCDG",7},
                {"ebs-VizYn33UI9EFUKmUlZI5zsH",7},
                {"ebs-vS67UkZjvMIGbcqIP9baj13",7},
                {"ebs-vYD5oCxXnUcWqagO0UUvtYw",5},
                {"ebs-xNN82A0iPenXfob5kFnfXek",7},
                {"ebs-Y7iM5eDt3Jzv5O3Au5jtnOf",6},
                {"ebs-yewo4lQdiB9Gpio2qCU7oCv",5},
                {"sys-295TzuvuWwHH5yTY6g0tZbc",6},
                {"sys-4H5otijCQUfqPwg8st7cseh",7},
                {"sys-61kWQIti55EqjA9c2PNyIlk",6},
                {"sys-9UT5JVAqcAtoAlHA86XG68P",7},
                {"sys-aWBxI1RdiuoAHAUWM0ZSgDV",7},
                {"sys-fzwVoDhHIYconDFnMVvvWEv",6},
                {"sys-HwxUmikFfY9Q1kZdlIEKWsM",7},
                {"sys-kiKRFbGsDpQxfQ7IPXCpipC",7},
                {"sys-KJpdtEePFPxRBRldoaFB9l3",5},
                {"sys-kL42bc9o3OFmbLLgNPXSGUl",7},
                {"sys-mlMH6AwDnCveKsAlRHRnGHe",7},
                {"sys-O44UupUmF5pjRP7zzu7uvZy",6},
                {"sys-reeTADCJjj7FBiB9rfN7KC2",6},
                {"sys-rObEDibQtVc5DNSOtbntvEX",5},
                {"sys-tHYzLVqzRmEM53eb2KcZkXt",7},
                {"sys-wI7emSYhQDhfdIt1JJ3gJn4",7},
                {"sys-y20CGWdZ9ZFXYEW6VoI92lP",5}
        };
    }
}
