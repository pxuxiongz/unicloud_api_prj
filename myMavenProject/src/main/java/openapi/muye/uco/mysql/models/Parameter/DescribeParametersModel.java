package openapi.muye.uco.mysql.models.Parameter;

import lombok.Data;

@Data
public class DescribeParametersModel {
    private String Action = "DescribeParameters";
    private String Engine = "mysql";
    private String DBInstanceId = "";
    private String RegionId = "";
}
