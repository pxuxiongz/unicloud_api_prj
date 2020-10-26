package openapi.muye.uco.redis.models.WhiteList;

import lombok.Data;

@Data
public class AddWhiteListModel {
    private String Action = "AddWhiteList";
    private String DBInstanceId = "";
    private String GroupName = "";
    private String IPList = "";
}
