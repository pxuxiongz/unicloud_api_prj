package muye.uca.postgresql.scripts;

import com.alibaba.fastjson.JSONObject;
import muye.uca.base.BaseInfo;
import muye.uca.postgresql.model.BackupAndRestoreModel;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
/**
 * @ Author :xx
 * @ Date : Created 11:41 2020/8/3
 * @ Description: 备份和恢复
 */
public class BackupAndRestore {
    String backupUrl = BaseInfo.url +"/postgresql/v1/bs/pgsql-x1n6ttkkfrp/backup";
    String modifyBackupRuleUrl = BaseInfo.url+"/postgresql/v1/bs/pgsql-x1n6ttkkfrp/backuprule";
    @Test
    public void Backup(){
        given().log().all()
                .contentType("application/json")
                .post(backupUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body("msg",is("Backup Begin"));
    }
    @Test
    public void ModifyBackupRule() throws InterruptedException {
        /**
         * @ Author :xx
         * @ Date : Created 15:37 2020/8/3
         * @ Param :[]
         * @ Return : void
         * @ Description: 修改备份规则
         */
        int times = 0;
        while(true) {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, 1);
            String backupAt = sdf.format(nowTime.getTime());
            BackupAndRestoreModel backupAndRestoreModel = new BackupAndRestoreModel();
            backupAndRestoreModel.setBackupId("763");
            backupAndRestoreModel.setBackupWeek("1,2,3,4,5,6,0");
            backupAndRestoreModel.setBackupAt(backupAt);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", backupAndRestoreModel.getBackupId());
            jsonBody.put("backupDayOfWeek", backupAndRestoreModel.getBackupWeek());
            jsonBody.put("backupAt", backupAndRestoreModel.getBackupAt());
            given().log().all()
                    .contentType("application/json")
                    .body(jsonBody.toJSONString())
                    .put(modifyBackupRuleUrl)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("code", is(10000));
            times += 1;
            System.out.printf("循环执行了%d次\r\n",times);
            Thread.sleep(240000);
        }

    }

}
