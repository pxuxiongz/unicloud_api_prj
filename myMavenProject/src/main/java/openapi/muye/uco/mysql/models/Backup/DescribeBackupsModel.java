package openapi.muye.uco.mysql.models.Backup;

import lombok.Data;

/**
 * @ Author :xx
 * @ Date : Created 11:28 2020/8/11
 * @ Description: 获取备份列表
 */
@Data
public class DescribeBackupsModel {
    private  String Action = "DescribeBackups";
    private  String DBInstanceId = "";
    private  int PageSize = 10;
    private int PageNumber = 1;
}
