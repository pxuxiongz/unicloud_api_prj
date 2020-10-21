package openapi.muye.uco.mysql.models.AccountManage;

import lombok.Data;

@Data
public class ResetAccountModel {
    private String Action = "ResetAccount";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private   String AccountName = "";
    private   String AccountPassword = "";
}
