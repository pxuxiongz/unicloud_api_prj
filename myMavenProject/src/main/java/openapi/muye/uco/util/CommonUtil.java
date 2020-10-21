package openapi.muye.uco.util;

import openapi.muye.uco.config.ConfigValue;
import sun.util.calendar.BaseCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonUtil {

    public static long getTimeStamp(){
        return System.currentTimeMillis();
    }


    public static Object[][] arrayCombination(Object[][] arrayOne, Object[][] arrayTwo){
        /**
         * @ Author :xx
         * @ Date : Created 13:57 2020/7/31
         * @ Param :[arrayOne, arrayTwo]
         * @ Return : java.lang.Object[][]
         * @ Description: 将两个二维数组进行交叉
         */
        Object[][] arrayThree = new Object[arrayOne.length * arrayTwo.length][];
        for (int oneRowNum = 0; oneRowNum < arrayOne.length; oneRowNum++) {
            for (int twoRowNum = 0; twoRowNum < arrayTwo.length; twoRowNum++) {
                int threeRowNum = oneRowNum * arrayTwo.length + twoRowNum;
                arrayThree[threeRowNum] = new Object[arrayOne[oneRowNum].length + arrayTwo[twoRowNum].length];
                for (int columnNum = 0; columnNum < arrayOne[oneRowNum].length + arrayTwo[twoRowNum].length; columnNum++) {
                    if (columnNum > arrayOne[oneRowNum].length - 1) {
                        arrayThree[threeRowNum][columnNum] = arrayTwo[twoRowNum][columnNum - arrayOne[oneRowNum].length];
                    } else {
                        arrayThree[threeRowNum][columnNum] = arrayOne[oneRowNum][columnNum];
                    }
                }
            }
        }
        return arrayThree;
    }

    public static void main(String[] args){
//        System.out.println(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
//        Date date = new Date();
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.MINUTE,5);
//        System.out.println(sdf.format(nowTime.getTime()));
//        int times = 1;
//        times += 1;
//        System.out.printf("循环执行了%d次\r\n",times);
        System.out.println(ConfigValue.ak);

    }
}
