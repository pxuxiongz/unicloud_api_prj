package openapi.muye.uco.mysql.models.DBManage;

import lombok.Data;

@Data
public class DescribeDatabasesModel {
    private String Action = "DescribeDatabases";
    private  String DBInstanceId = "";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private int PageSize = 10;
    private int PageNumber = 1;
}
