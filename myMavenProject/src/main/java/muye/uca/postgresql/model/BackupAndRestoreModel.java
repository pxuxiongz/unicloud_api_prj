package muye.uca.postgresql.model;

import lombok.Data;

/**
 * @ Author :xx
 * @ Date : Created 11:42 2020/8/3
 * @ Description: 备份和恢复的Model
 */
@Data
public class BackupAndRestoreModel {

    //备份规则
    private String backupId = "";
    private String backupWeek = "";
    private String backupAt = "";

}
