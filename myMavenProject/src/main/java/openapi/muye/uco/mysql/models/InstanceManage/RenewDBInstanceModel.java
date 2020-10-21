package openapi.muye.uco.mysql.models.InstanceManage;

import lombok.Data;

@Data
public class RenewDBInstanceModel {
    private String Action = "RenewDBInstance";
    private String Engine = "mysql";
    private String DBInstanceId = "";
    private String RegionId = "cn-tianjin-cto";

    private  int UsedTime = 1;//购买时长，单位：月。PayType为YEAR_MONTH必选。取值：1,2,3,6,12
    private  String Period = "month"; //购买时长单位。PayType为YEAR_MONTH必选。目前只支持month

}
