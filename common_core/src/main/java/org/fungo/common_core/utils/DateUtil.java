package org.fungo.common_core.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * yyyy-MM-dd HH:mm
     */
    public final static DateFormat dateTimeMinuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * yyyy-MM-dd HH:mm:ss
     **/
    public final static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * yyyy-MM-dd
     */
    public final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * M月d日 HH:mm E
     */
    public final static DateFormat dateWeekCnFormat = new SimpleDateFormat("M月d日 HH:mm E");
    /**
     * M月d日 HH:mm
     */
    public final static DateFormat dateCnFormat = new SimpleDateFormat("M月d日 HH:mm");
    /**
     * M-d HH:mm
     */
    public final static DateFormat dateSimpleTimeFormat = new SimpleDateFormat("M-d HH:mm");

    public final static DateFormat simpleDate = new SimpleDateFormat("M月d日");
    /**
     * HH:mm:ss
     */
    public final static DateFormat time = new SimpleDateFormat("HH:mm:ss");
    /**
     * HH:mm
     */
    public final static DateFormat simpleTime = new SimpleDateFormat("HH:mm");
    /**
     * mm:ss
     */
    public final static DateFormat shortVideoTime = new SimpleDateFormat("mm:ss");
    /**
     * hh:mm
     */
    public final static DateFormat smallSimpleTime = new SimpleDateFormat("hh:mm");
    /**
     * yyyyMMddHHmm
     **/
    public final static DateFormat year2minute = new SimpleDateFormat("yyyyMMddHHmm");
    /**
     * yyyyMMddHHmmss
     **/
    public final static DateFormat year2second = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * yyyyMMdd HH:mm
     **/
    public final static DateFormat year2minute2 = new SimpleDateFormat("yyyyMMdd HH:mm");
    /**
     * yyyyMMdd
     **/
    public final static DateFormat year2day = new SimpleDateFormat("yyyyMMdd");
    /**
     * yyyyMMdd HH:mm:ss
     **/
    public final static DateFormat configTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    /**
     * 格式周*，0=周日
     **/
    public final static String[] weekFormat1 = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static Date monthBegin(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTime();
    }

    public static Date yearBegin(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTime();
    }

    public static Date dayBegin(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTime();
    }


    public static Date getDateFromFormat(DateFormat df, String date) {
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到星期几
     *
     * @param date
     * @return 0=星期天，1=星期一，以此类推
     */
    public static int getWeek(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        return c1.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static int yearBetween(Date begin, Date end) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(begin);
        int i = ca.get(Calendar.YEAR);
        ca.setTime(end);
        int j = ca.get(Calendar.YEAR);
        return j - i;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }


    public static int dayBetween(Date from, Date to) {
        return dayBetween(from.getTime(), to.getTime());
    }

    public static int dayBetween(long from, long to) {
        return (int) ((to - from) / (86400000));
    }

    public static int hourBetween(Date from, Date to) {
        return (int) ((to.getTime() - from.getTime()) / (3600000));
    }

    public static int minuteBetween(Date from, Date to) {
        return (int) ((to.getTime() - from.getTime()) / (60000));
    }

    public static int secondBetween(Date from, Date to) {
        return (int) ((to.getTime() - from.getTime()) / (1000));
    }

    public static int serverTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int serverTime(Date date) {
        return (int) (date.getTime() / 1000);
    }


    public static void main(String[] args) {
//        System.out.println(DateUtil.getDateFromFormat(DateUtil.time, "13:20:00"));
        Date date = new Date();
        int time = (int) (date.getTime() / 1000);
        String md5Url = "";
        try {
            md5Url = MD5Utils.getStringMD5String("fungolive" + time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sign=" + md5Url + "&nwtime=" + time);
    }

    //如果比赛开始时间已经过去2个小时，则认为比赛已结束，删除该记录
    public static boolean isShowOver(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar schedule = Calendar.getInstance();
        schedule.setTime(date);

        long intervalMilli = now.getTimeInMillis() - schedule.getTimeInMillis();

        if (intervalMilli > 60 * 60 * 1000 * 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回时间差的分钟值，用于判断 显示格式
     *
     * @param date
     * @return
     */
    public static long getIntervalTimeMinutes(Date date) {
        return getIntervalMilli(date) / 1000 / 60;
    }

    //获取时间差的毫秒值
    private static long getIntervalMilli(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar schedule = Calendar.getInstance();
        schedule.setTime(date);
        return now.getTimeInMillis() - schedule.getTimeInMillis();
    }

    public static String buildEventTimeOffsetStr(int m) {
        return m < 60 ? m + "分钟后" : (m < 1440 ? m / 60 + "小时后" : m / 1440 + "天后");
    }


    /**
     * 判断比赛是否正在进行
     *
     * @param date
     * @return
     */
    public static boolean isMatchStart(Date date) {
        long time = getIntervalMilli(date) / 1000 / 60;
        return time >= 0 && time < 180;
    }

    public static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    /**
     * <pre>
     * 判断date和当前日期是否在同一周内
     * 注:
     * Calendar类提供了一个获取日期在所属年份中是第几周的方法，对于上一年末的某一天
     * 和新年初的某一天在同一周内也一样可以处理，例如2012-12-31和2013-01-01虽然在
     * 不同的年份中，但是使用此方法依然判断二者属于同一周内
     * </pre>
     *
     * @param date
     * @return
     */
    public static boolean isSameWeekWithToday(Date date) {

        if (date == null) {
            return false;
        }

        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);

        // 1.比较当前日期在年份中的周数是否相同
        if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }
}
