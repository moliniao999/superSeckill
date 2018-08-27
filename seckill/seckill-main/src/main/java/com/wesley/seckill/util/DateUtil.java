package com.wesley.seckill.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 时间处理工具类
 */
public class DateUtil {

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd hh:mm:ss";
    public static final String YYYYMMDDMIN = "yyyy-MM-dd 00:00:00";
    public static final String YYYYMMDDMAX = "yyyy-MM-dd 23:59:59";

    public static final ZoneId chinaZone = ZoneId.of("UTC+08:00");

    /**
     * 时间格式化
     *
     * @param date   时间
     * @param format 格式化 如：yyyyMMdd HH:mm:ss
     * @return
     */
    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 当前时间格式化 返回yyyy-MM-dd hh:mm:ss格式
     *
     * @return
     */
    public static String nowStr() {
        return dateFormat(new Date(), YYYYMMDDHHMMSS);
    }

    /**
     * 加上指定的月份
     *
     * @param date     时间
     * @param monthNum 想加的月数 为负数 则是向前推相应的月数
     * @return
     */
    public static Date addMonths(Date date, int monthNum) {
        return DateUtils.addMonths(date, monthNum);
    }

    /**
     * 加上指定的天数
     *
     * @param date     时间
     * @param dayNum 想加的月数 为负数 则是向前推相应的天数
     * @return
     */
    public static Date addDays(Date date, int dayNum) {
        return DateUtils.addDays(date, dayNum);
    }

    /**
     * 将字符串时间格式 转换为date类型
     *
     * @param dateStr 时间格式字符串
     * @param format  时间格式和dateStr对应 如: dateStr为2017-08-07 13:00:00 则dateStr必须为yyyy-MM-dd hh:mm:ss格式
     * @return
     * @throws ParseException
     */
    public static Date strConverDate(String dateStr, String format) throws ParseException {
        return DateUtils.parseDate(dateStr, format);
    }


    /**
     * LocalDateTime对象转为DateTime对象
     *
     * @param localDateTime localDateTime
     * @return Date类型对象
     */
    public static Date localDateTime2DateTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }

    /**
     * DateTime对象转换为LocalDateTime对象
     *
     * @param datetime 转换对象
     * @return 日期对象
     */
    public static LocalDateTime DateTime2LocalDateTime(Date datetime) {
        Instant timestamp = datetime.toInstant();
        //Now we can convert Instant to LocalDateTime or other similar classes
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp,
                ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        return localDateTime;
    }


    /**
     * 日期时间对象转换为日期对象
     *
     * @param localDateTime 日期时间对象
     * @return 日期对象
     */
    public static LocalDate localDateTime2LocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();

    }

    /**
     * 日期对象转换为日期对象
     *
     * @param localDate 日期对象
     * @return 日期时间对象
     */
    public static LocalDateTime localDate2LocalDateTIme(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.NOON);
    }


    /**
     * 字符串转换为日期
     *
     * @param strDate 字符串日期
     * @return 日期对象 yyyy-mm-dd
     */
    public static Date str2Date(String strDate) {
        LocalDate localDate = LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
        //LocalDateTime localDateTime = localDate.atTime(LocalTime.MIN);
        Instant instant = localDate2LocalDateTIme(localDate).atZone(chinaZone).toInstant();
        return Date.from(instant);
    }

    /**
     * 字符串转换为日期时间对象
     *
     * @param strDateTime 字符串日期
     * @return 日期对象 yyyy-mm-dd
     */
    public static Date str2DateTime(String strDateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(strDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //LocalDateTime localDateTime = localDate.atTime(LocalTime.MIN);
        Instant instant = localDateTime.atZone(chinaZone).toInstant();
        return Date.from(instant);
    }

    /**
     * 字符串转换为日期
     *
     * @param strDate 字符串日期
     * @return 日期对象 yyyy-mm-dd
     */
    public static LocalDate str2LocalDate(String strDate) {
        return LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
    }


    /**
     * 日期对象转换为字符串
     *
     * @param localDate 日期对象
     * @return 日期字符串 yyyy-mm-dd
     */
    public static String localDate2Str(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }


    /**
     * 日期时间对象转换为字符串
     *
     * @param localDateTime     日期时间对象
     * @param dateTimeFormatter 格式化字符串
     * @return 日期字符串
     */
    public static String localDateTime2Str(LocalDateTime localDateTime, String dateTimeFormatter) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * 日期时间转字符串函数
     * 返回ISO标准的日期字符串
     *
     * @param localDateTime 日期时间对象
     * @return 日期字符串
     */
    public static String localDateTime2Str(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }


    /**
     * 日期时间转字符串函数
     * 返回yyyy-MM-dd HH:mm:ss格式的日期字符串
     *
     * @param dateTime 日期时间对象
     * @return 日期字符串
     */
    public static String dateTime2Str(Date dateTime) {
        Instant instantDate = dateTime.toInstant();
        LocalDateTime localDate1 = instantDate.atZone(chinaZone).toLocalDateTime();
        return localDate1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int daysBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int monthsBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getMonths();
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int yearsBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getYears();
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static int daysBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(chinaZone).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(chinaZone).toLocalDate();
        instantDate1.atZone(chinaZone);
        Period period = Period.between(localDate1, localDate2);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static int monthsBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(chinaZone).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(chinaZone).toLocalDate();
        instantDate1.atZone(chinaZone);
        Period period = Period.between(localDate1, localDate2);
        return period.getMonths();
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static int yearsBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(chinaZone).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(chinaZone).toLocalDate();
        instantDate1.atZone(chinaZone);
        Period period = Period.between(localDate1, localDate2);
        return period.getYears();
    }

    /**
     * 获取指定日期对象当前月的起始日
     *
     * @param localDate 指定日期
     * @return
     */
    public static int getFirstDayInMonth(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return result.getDayOfMonth();

    }

    /**
     * 获取指定日期对象的当前月的结束日
     *
     * @param localDate 指定日期
     * @return
     */
    public static int getLastDayInMonth(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return result.getDayOfMonth();
    }


    /**
     * 获取指定日期对象本月的某周某天的日期
     *
     * @param localDate  日期对象
     * @param weekNumber 周
     * @param dayNumber  日
     * @return
     */
    public static LocalDate getLocalDateBydayAndWeek(LocalDate localDate, int weekNumber, int dayNumber) {
        return localDate.with(TemporalAdjusters.dayOfWeekInMonth(weekNumber, DayOfWeek.of(dayNumber)));
    }

}
