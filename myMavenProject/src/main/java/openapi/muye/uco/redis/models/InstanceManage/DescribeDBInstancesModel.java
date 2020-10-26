package openapi.muye.uco.redis.models.InstanceManage;

import lombok.Data;

@Data
public class DescribeDBInstancesModel {
    private String Action = "DescribeDBInstances";
    private String RegionId = "cn-tianjin-cto";
    private int PageSize = 10;
    private int PageNumber = 1;
}
