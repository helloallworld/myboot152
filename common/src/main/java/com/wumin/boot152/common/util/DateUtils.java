package com.wumin.boot152.common.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static final String[] DATE_PARSE_PATTERNS = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    /**
     * 将Date类转换为XMLGregorianCalendar
     *
     * @param date
     * @return
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date) {
        TimeZone zone = TimeZone.getTimeZone("GMT+8");
        Calendar cal = Calendar.getInstance(zone);
        cal.setTime(date);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
        }
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        // 由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH) + 1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        /*dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));*/
        dateType.setHour(0);
        dateType.setMinute(0);
        dateType.setSecond(0);
        return dateType;
    }

    /**
     * 将XMLGregorianCalendar转换为Date
     *
     * @param cal
     * @return
     */
    public static Date xmlDate2Date(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    /**
     * 格式化日期
     *
     * @param dateStr 字符型日期
     * @param format  格式
     * @return 返回日期
     */

    public static Date parseDate(java.sql.Date date) {
        return date;
    }

    public static java.sql.Date parseSqlDate(Date date) {
        if (date != null)
            return new java.sql.Date(date.getTime());
        else
            return null;
    }

    /**
     * 得到当天的时间。凌晨开始
     */
    public static String getToday() {
        return format(new Date(), "yyyy-MM-dd") + " 00:00:00";
    }

    /**
     * 格式化输出日期
     *
     * @param date   日期
     * @param format 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String stly) {
        Locale locale = null;
        if (stly.indexOf("MMM") != -1) {
            locale = new Locale("en");
        }
        String result = "";
        String str = "yyyy-MM-dd HH:mm:ss";
        if (stly != null && !stly.equals(""))
            str = stly;
        try {
            if (date != null) {
                if (locale == null) {
                    java.text.DateFormat df = new SimpleDateFormat(str);
                    result = df.format(date);
                } else {
                    java.text.DateFormat df = new SimpleDateFormat(str, locale);
                    result = df.format(date);
                }

            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 返回年份
     *
     * @param date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     *
     * @param date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        String result = "";
        switch (dayForWeek){
            case 1: result = "星期一";break;
            case 2: result = "星期二";break;
            case 3: result = "星期三";break;
            case 4: result = "星期四";break;
            case 5: result = "星期五";break;
            case 6: result = "星期六";break;
            case 7: result = "星期日";break;
        }
        return result;
    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期
     */
    public static String getDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 返回字符型时间
     *
     * @param date 日期
     * @return 返回字符型时间
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回date - date1 相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * @param @param  date
     * @param @param  date1
     * @param @return 参数
     * @return int    返回类型：毫秒
     * @Title: diffSecond
     * @Description: TODO(比较时间)
     */
    public static Long diffSecond(Date date, Date date1) {
        return getMillis(date) - getMillis(date1);
    }

    public static int getDiffYearLikeAge(Date date) {
        if (date == null) {
            return 0;
        }
        int thisyear = getYear(new Date());
        int thatyear = getYear(date);

        return thisyear - thatyear;
    }

    /**
     * 判断日期是否比现在靠后
     *
     * @param date
     * @return
     */
    public static boolean isbefortoday(Date date) {
        boolean ok = true;
        if (getMillis(date) - getMillis(new Date()) > 60000) {
            ok = false;
        }
        return ok;
    }

    /**
     * 返回日期与今天相差的天数 today-datestr
     *
     * @param today
     * @param datestr
     * @return
     */
    public static int diffDate(String datestr) {

        return (int) ((getMillis(new Date()) - getMillis(getDate(datestr))) / (24 * 3600 * 1000));
    }

    /**
     * @param datestr 时间总秒数
     * @return
     */
    public static Long diffDateBySecond(String datestr) {
        // 发布时间与当前时间相差天数
        return (getMillis(new Date()) / 1000 - Long.parseLong(datestr)) / (24 * 3600);
    }

    public static boolean isSameDay(Date date, Date date1) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);
        c2.setTime(date1);
        int c1_day = c1.get(Calendar.DAY_OF_YEAR);
        int c1_y = c1.get(Calendar.YEAR);

        int c2_day = c2.get(Calendar.DAY_OF_YEAR);
        int c2_y = c2.get(Calendar.YEAR);

        if (c1_day == c2_day && c1_y == c2_y) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getDate(String datestr) {
        String format = "";
        if (datestr.length() <= 10) {
            format = "yyyy-MM-dd";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sd = new SimpleDateFormat(format);
        try {
            Date d = sd.parse(datestr);
            return d;
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 获取当前年份 <br>
     *
     * @param @return
     * @return String
     * @throws
     */
    public static String getCurYear() {
        return getFormatCurTime("yyyy");
    }

    /**
     * 获取当前月份 <br>
     *
     * @param @return
     * @return String
     * @throws
     */
    public static String getCurMonth() {
        return getFormatCurTime("MM");
    }

    /**
     * 获取当前日期 <br>
     *
     * @param @return
     * @return String
     */
    public static String getCurDay() {
        return getFormatCurTime("dd");
    }

    /**
     * 获取当前日期。 <br>
     *
     * @param format 格式(比如：yyyyMMdd yyyy/MM/dd HH:MM:ss)
     * @return String - 返回当前日期
     */
    public static String getFormatCurTime(String format) {
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        Date date = new Date();
        String dateString = dataFormat.format(date);
        return dateString;
    }

    /**
     * @param @return
     * @return long
     * @throws
     * @Title: getCurrentTimeMillis
     * @Description: 获取当前系统时间戳
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期。 <br>
     * 格式 yyyyMMdd
     *
     * @return String - 返回当前日期
     */
    public static String getCurDate() {
        return getFormatCurTime("yyyyMMdd");
    }

    /**
     * 时间格式为yyyy-MM-dd HH:mm:ss
     *
     * @author BY--dong.yao--AT--2018年1月20日下午2:26:53
     */
    public static Long getLongTime(String datestr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2 = sdf.parse(datestr);
        return dt2.getTime();
    }

    public static Long getRedisExpireNextDay() {
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        long l = getMillis(calendar.getTime());
        return l - getCurrentTimeMillis();
    }

    public static void main(String[] args) throws ParseException {
        getRedisExpireNextDay();
    }

    public static Date parseDate(String stringDate) {
        //建立一个一定格式的 SimpleDateFormat
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date odate = null;
        try { //从字符串产生 Date 需要 try/catch
            odate = f.parse(stringDate); //将字符串转化为Date
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return odate;
    }
}
