package openapi.muye.uco.mysql.models.InstanceManage;

import lombok.Data;

@Data
public class DescribeDBInstanceAttributeModel {
    private String Action = "DescribeDBInstanceAttribute";
    private String Engine = "mysql";
    private String DBInstanceId = "";
    private String RegionId = "cn-tianjin-cto";


}
