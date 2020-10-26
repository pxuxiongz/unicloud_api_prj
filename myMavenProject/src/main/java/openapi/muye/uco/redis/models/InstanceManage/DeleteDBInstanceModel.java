package openapi.muye.uco.redis.models.InstanceManage;

import lombok.Data;

@Data
public class DeleteDBInstanceModel {
    private String Action = "DeleteDBInstance";
    private String DBInstanceId = "";
}
