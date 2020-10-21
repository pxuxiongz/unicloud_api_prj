package openapi.muye.uco.mysql.models.Backup;

import lombok.Data;

/**
 * @ Author :xx
 * @ Date : Created 11:21 2020/8/11
 * @ Description: 设置备份规则Model
 */
@Data
public class ModifyBackupPolicyModel {
    private String Action = "ModifyBackupPolicy";
    private String DBInstanceId = "";
    private String BackupPeriod = "";
    private String BackupTime = "";
}
