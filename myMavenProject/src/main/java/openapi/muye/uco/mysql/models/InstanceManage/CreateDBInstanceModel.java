package openapi.muye.uco.mysql.models.InstanceManage;

import lombok.Data;

@Data
public class CreateDBInstanceModel {
    private String Action = "CreateDBInstance";
    private String Engine = "mysql";
    private String RegionId = "cn-tianjin-cto";
    private int Port = 3306;
    private String EngineVersion = "5.7";
    private String AzId = "";
    private String InstanceMode = "SE";
    private  String InstanceType = "normal";
    private String VpcId = "";
    private  String VpcSubnetId = "";
    private String InstanceClass = "db.c1.medium";
    private  int InstanceStorage = 13;
    private String InstanceStorageType = "local";
    private String PayType = "YEAR_MONTH"; //计费方式。取值：-YEAR_MONTH（包年包月） -CHARGING_HOURS（按小时计费） -DAY_MONTH（按日月结）
    private  int Quantity = 1; //购买实例个数，取值:1~5
    //以下非必填
    private  String AzIdSlave = ""; //备节点可用区ID,高可用版实例必选
    private  String SourceInstanceId = ""; //源实例ID，当InstanceType为read_only时必选
    private  int UsedTime = 1;//购买时长，单位：月。PayType为YEAR_MONTH必选。取值：1,2,3,6,12
    private  String Period = "month"; //购买时长单位。PayType为YEAR_MONTH必选。目前只支持month
    private  String RenewType = "manualrenew"; //续费类型，PayType为YEAR_MONTH必选。取值：-autorenew：自动续费 -manualrenew：手动续费（到期提醒） -notrenew：到期不续费（无到期提醒）
}
