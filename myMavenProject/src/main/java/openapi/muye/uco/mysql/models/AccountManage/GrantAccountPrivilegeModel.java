package openapi.muye.uco.mysql.models.AccountManage;

import lombok.Data;

@Data
public class GrantAccountPrivilegeModel {
    private String Action = "GrantAccountPrivilege";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private String AccountName = "";
    private  String DBName = "";
    private String  AccountPrivilege = "";

}
