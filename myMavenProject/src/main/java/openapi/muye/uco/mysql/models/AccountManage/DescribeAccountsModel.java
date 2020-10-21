package openapi.muye.uco.mysql.models.AccountManage;

import lombok.Data;

@Data
public class DescribeAccountsModel {
    private  String Action = "DescribeAccounts";
    private  String DBInstanceId = "";
    private  String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private int PageSize = 10;
    private int PageNumber = 1;
}
