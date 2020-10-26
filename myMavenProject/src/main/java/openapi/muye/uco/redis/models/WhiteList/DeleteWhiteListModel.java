package openapi.muye.uco.redis.models.WhiteList;

import lombok.Data;

@Data
public class DeleteWhiteListModel {
    private String Action = "DeleteWhiteList";
    private String DBInstanceId = "";
    private String GroupName = "";
}
