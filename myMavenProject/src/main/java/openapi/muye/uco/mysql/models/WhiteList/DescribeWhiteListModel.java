package openapi.muye.uco.mysql.models.WhiteList;

import lombok.Data;

@Data
public class DescribeWhiteListModel {
    private String Action = "DescribeWhiteList";
    private String DBInstanceId = "";
    private String GroupName = "";
    private String IPList = "";
    private String Engine = "mysql";
    private  String RegionId = "";
}
