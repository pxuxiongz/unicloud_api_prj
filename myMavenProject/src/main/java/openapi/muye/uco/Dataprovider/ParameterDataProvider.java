package openapi.muye.uco.Dataprovider;

import openapi.muye.uco.util.CommonUtil;
import org.testng.annotations.DataProvider;

/**
 * @ Author :xx
 * @ Date : Created 13:46 2020/7/29
 * @ Description: param校验
 */
public class ParameterDataProvider {
    public static Object[][] EngineModeInstance(){
        return new Object[][]{
                {"mysql","SE"},
                {"mysql","HA"}
        };
    }

    @DataProvider(name = "ModifyParameter")
    public  static Object[][] ModifyParameter(){
        Object[][] o1 =  new Object[][]{
                {"binlog_checksum","crc32"},
                {"binlog_checksum","none"},
                {"connect_timeout","31536000"},
                {"connect_timeout","31535999"},
                {"connect_timeout","2"},
                {"connect_timeout","10"},
                {"delay_key_write","off"},
                {"delay_key_write","all"},
                {"delay_key_write","on"},
                {"delayed_queue_size","1"},
                {"delayed_queue_size","18446744073709551615"},
                {"delayed_queue_size","18446744073709551614"},
                {"delayed_queue_size","1000"},
                {"expire_logs_days","99"},
                {"expire_logs_days","0"},
                {"sql_mode","allow_invalid_dates,ansi_quotes,error_for_division_by_zero,high_not_precedence,ignore_space,no_auto_create_user,no_auto_value_on_zero,no_backslash_escapes,no_dir_in_create,no_engine_substitution,no_field_options,no_key_options,no_table_options,no_unsigned_subtraction,no_zero_date,no_zero_in_date,only_full_group_by,pad_char_to_full_length,pipes_as_concat,real_as_float,strict_all_tables,strict_trans_tables"},
                {"sql_mode","only_full_group_by,strict_trans_tables,no_zero_in_date,no_zero_date,error_for_division_by_zero,no_auto_create_user,no_engine_substitution"}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }
    @DataProvider(name = "ModifyParameterInvalid")
    public static Object[][] ModifyParameterFail(){
        Object[][] o1 =  new Object[][]{
                {"innodb_adaptive_hash_index","false"},
                {"innodb_concurrency_tickets","4294967296"},
                {"innodb_concurrency_tickets","-1"},
                {"innodb_stats_method","abc"},
                {"","false"},
                {"innodb_adaptive_hash_index",""}
        };
        return  CommonUtil.arrayCombination(EngineModeInstance(),o1);
    }


}
