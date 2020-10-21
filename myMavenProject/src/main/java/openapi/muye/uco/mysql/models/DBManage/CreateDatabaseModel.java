package openapi.muye.uco.mysql.models.DBManage;

import lombok.Data;

@Data
public class CreateDatabaseModel {
    private String Action = "CreateDatabase";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private  String DBName = "";
    private String CharacterSetName  = "utf8";
}
