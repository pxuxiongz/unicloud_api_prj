package openapi.muye.uco.mysql.models.WhiteList;

import lombok.Data;

@Data
public class ModifyWhiteListModel {
    private String Action = "ModifyWhiteList";
    private String DBInstanceId = "";
    private String GroupName = "";
    private String IPList = "";
    private String GroupId = "";
    private String Engine = "mysql";
    private  String RegionId = "";
}
