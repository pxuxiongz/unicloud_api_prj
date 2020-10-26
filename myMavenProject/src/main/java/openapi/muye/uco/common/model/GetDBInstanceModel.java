package openapi.muye.uco.common.model;

import lombok.Data;
import openapi.muye.uco.config.ConfigValue;

/**
 * @ Author :xx
 * @ Date : Created 15:56 2020/10/22
 * @ Description: 获取实例的通用Model
 */
@Data
public class GetDBInstanceModel {
        private  String InstanceMode = ""; //SE  HA
        private  String Engine = ""; //mysql postgresql sqlserver
        private  String InstanceType = "normal";//实例类型。取值：-normal：常规实例 -read_only：只读实例
        private  String PayType = "";//计费方式。取值：-YEAR_MONTH（包年包月） -CHARGING_HOURS（按小时计费） -DAY_MONTH（按日月结）
        private  String RenewType = ""; //续费类型，PayType为YEAR_MONTH必选。取值：-autorenew：自动续费 -manualrenew：手动续费（到期提醒） -notrenew：到期不续费（无到期提醒）
        private String RegionId = ConfigValue.RegionId;
        private double Version = 0.0;
}
