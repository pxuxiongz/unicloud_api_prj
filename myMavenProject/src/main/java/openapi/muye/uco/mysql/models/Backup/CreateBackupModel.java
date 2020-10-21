package openapi.muye.uco.mysql.models.Backup;

import lombok.Data;

/**
 * @ Author :xx
 * @ Date : Created 11:23 2020/8/11
 * @ Description: 创建手动备份
 */
@Data
public class CreateBackupModel {
    private String Action = "CreateBackup";
    private  String DBInstanceId = "";
}
