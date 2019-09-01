package com.example.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;

import com.example.utils.DateUtils;

public class NormalTest {
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 需要转化成的时间格式

    public static void main(String[] args) throws ParseException {
	String dateStr = "20190301";
	int dis = -2;
	String format = "yyyyMMdd";
	System.out.println(DateUtils.getNextDay(dateStr, format, dis));
	//testTime1();
	//utcToLocal("2017-05-18 10:26:10.488");
	Date date = localToUTC("2019-08-15 20:10:00");
	System.out.println(sdf.format(date));
    }

    public static void testTime1() throws ParseException {
	String dateStr = "2017-05-18T10:26:10.488Z";
	SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",Locale.ROOT);// 输入的被转化的时间格式
	//dff.setTimeZone(TimeZone.getTimeZone("UTC"));
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 需要转化成的时间格式
	df1.setTimeZone(TimeZone.getTimeZone("GMT-8"));
	SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
	df2.setTimeZone(TimeZone.getTimeZone("GMT-8"));
	Date date1 = dff.parse(dateStr);
	String str1 = df1.format(date1);
	String str2 = df2.format(date1);
	System.out.println("str1 is " + str1);
	System.out.println("str2 is " + str2);
    }
    
    public static String utcToLocal(String utcTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = sdf2.format(locatlDate);
        System.out.println(str);
        return str;
    }
    
    public static long utcToTimestamp(DateTime dataTime) throws ParseException {
	   SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	   df2.setTimeZone(TimeZone.getTimeZone("UTC"));
	   Date date = df2.parse(dataTime.toString());
	   return date.getTime();
    }
    
    /**
     * 
     * Description: 本地时间转化为UTC时间
     * @param localTime
     * @return
     * @author wgs 
     * @date  2018年10月19日 下午2:23:43
     *
     */
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate= null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis=localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate=new Date(calendar.getTimeInMillis());
        return utcDate;
    }
}
