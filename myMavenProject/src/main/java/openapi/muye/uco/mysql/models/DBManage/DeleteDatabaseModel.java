package openapi.muye.uco.mysql.models.DBManage;

import lombok.Data;

@Data
public class DeleteDatabaseModel {
    private String Action = "DeleteDatabase";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private  String DBName = "";
}
