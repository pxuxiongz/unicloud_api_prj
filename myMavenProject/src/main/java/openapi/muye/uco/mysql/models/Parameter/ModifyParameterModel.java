package openapi.muye.uco.mysql.models.Parameter;

import lombok.Data;

@Data
public class ModifyParameterModel {
    private String Action = "ModifyParameter";
    private String Engine = "mysql";
    private String DBInstanceId = "";
    private String RegionId = "";
    private String paramName = "";
    private String paramValue = "";
}
