package openapi.muye.uco.mysql.models.AccountManage;

import lombok.Data;

@Data
public class RevokeAccountPrivilegeModel {
    private String Action = "RevokeAccountPrivilege";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private   String AccountName = "";
    private String RevokeDBList = "";
}
