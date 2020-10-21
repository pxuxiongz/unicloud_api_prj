package openapi.muye.uco.mysql.models.InstanceManage;

import lombok.Data;

@Data
public class DeleteDBInstanceModel {
    private String Action = "DeleteDBInstance";
    private String Engine = "mysql";
    private String DBInstanceId = "";
    private String RegionId = "cn-tianjin-cto";

}
