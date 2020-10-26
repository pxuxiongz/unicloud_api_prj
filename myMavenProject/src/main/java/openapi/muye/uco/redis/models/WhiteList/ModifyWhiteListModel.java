package openapi.muye.uco.redis.models.WhiteList;

import lombok.Data;

@Data
public class ModifyWhiteListModel {
    private String Action = "ModifyWhiteList";
    private String DBInstanceId = "";
    private String GroupName = "";
    private String IPList = "";
    private String GroupId = "";
}
