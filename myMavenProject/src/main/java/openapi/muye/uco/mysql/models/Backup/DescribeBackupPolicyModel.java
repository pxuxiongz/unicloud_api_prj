package openapi.muye.uco.mysql.models.Backup;

import lombok.Data;

/**
 * @ Author :xx
 * @ Date : Created 11:19 2020/8/11
 * @ Description: 获取备份规则Model
 */
@Data
public class DescribeBackupPolicyModel {

    private String Action = "DescribeBackupPolicy";
    private  String DBInstanceId = "";

}
