package openapi.muye.uco.mysql.models.AccountManage;

import lombok.Data;

@Data
public class DeleteAccountModel {
    private String Action = "DeleteAccount";
    private String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private String AccountName = "";
}
