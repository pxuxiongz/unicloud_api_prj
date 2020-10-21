package openapi.muye.uco.mysql.models.WhiteList;

import lombok.Data;

@Data
public class DeleteWhiteListModel {
    private String Action = "DeleteWhiteList";
    private String DBInstanceId = "";
    private String GroupName = "";
    private String Engine = "mysql";
    private  String RegionId = "";
}
