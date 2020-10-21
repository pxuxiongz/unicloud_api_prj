package openapi.muye.uco.mysql.models.AccountManage;


import lombok.Data;

@Data
public class CreateAccountModel {
    private   String AccountName = "";
    private   String AccountPassword = "";
    private  String AccountType = "normal";
    private String Action = "CreateAccount";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";

}
