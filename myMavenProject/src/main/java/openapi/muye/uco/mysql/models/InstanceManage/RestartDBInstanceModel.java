package openapi.muye.uco.mysql.models.InstanceManage;

import lombok.Data;

@Data
public class RestartDBInstanceModel {
    private String Action = "RestartDBInstance";
    private String Engine = "mysql";
    private String DBInstanceId = "";
    private String RegionId = "cn-tianjin-cto";


}
