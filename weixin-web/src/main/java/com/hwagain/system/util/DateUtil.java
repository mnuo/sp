package com.hwagain.system.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
     * 获取上(下)周周几的日期
     * 
     * @param firstDayOfWeek {@link Calendar} 值范围 <code>SUNDAY</code>,
     *                       <code>MONDAY</code>, <code>TUESDAY</code>,
     *                       <code>WEDNESDAY</code>, <code>THURSDAY</code>,
     *                       <code>FRIDAY</code>, and <code>SATURDAY</code>
     * @param dayOfWeek      {@link Calendar}
     * @param weekOffset     周偏移，上周为-1，本周为0，下周为1，以此类推
     * @return
     */
	public static Date getDayOfWeek(int firstDayOfWeek, int dayOfWeek, int weekOffset) {
        if (dayOfWeek > Calendar.SATURDAY || dayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        if (firstDayOfWeek > Calendar.SATURDAY || firstDayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        
        Calendar date = Calendar.getInstance(Locale.CHINA);
        date.setFirstDayOfWeek(firstDayOfWeek);
        // 周数减一，即上周
        date.add(Calendar.WEEK_OF_MONTH, weekOffset);
        // 日子设为周几
        date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        // 时分秒全部置0
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }
	public static String getDayOfNextWeek(int dayOfWeek) {
        Calendar date = Calendar.getInstance(Locale.CHINA);
        date.setFirstDayOfWeek(Calendar.MONDAY);
        // 周数减一，即上周
        date.add(Calendar.WEEK_OF_MONTH, 1);
        // 日子设为周几
        date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        // 时分秒全部置0
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        DateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		try{
		    return format.format(date.getTime());
		}catch(Exception e){
		    e.printStackTrace();
		}
        return null;
    }
	
	public static String formatString(Date date){
		return formatString(date, "yyyy-MM-dd");
	}

	public static String formatString(Date date, String patter){
		String str = null;
		DateFormat format =  new SimpleDateFormat(patter);
		try{
			str = format.format(date);
		}catch(Exception e){
		    e.printStackTrace();
		}
		return str;
	}
	public static Date parse(String str){
		return parse(str, "yyyy-MM-dd");
	}

	public static Date parse(String str, String patter){
		Date date = null;
		DateFormat format =  new SimpleDateFormat(patter);
		try{
			date = format.parse(str);
		}catch(Exception e){
		    e.printStackTrace();
		}
		return date;
	}

	public static String getNextDay(String str, int diff) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(parse(str));
		cal.add(Calendar.DAY_OF_MONTH, diff);
		// 时分秒全部置0
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		try{
		    return formatString(cal.getTime());
		}catch(Exception e){
		    e.printStackTrace();
		}
        return "";
	}
	/**
	 * 
	 * @param currentTime   要比较的时间
	 * @param startTime   开始时间
	 * @param endTime   结束时间
	 * @return   true在时间段内，false不在时间段内
	 * @throws Exception
	 */
	public static boolean hourMinuteBetween(String currentTime, String startTime, String endTime) throws Exception{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Date currentDate = format.parse(currentTime);
		Date startDate = format.parse(startTime);
		Date endDate = format.parse(endTime);
		
		long current = currentDate.getTime();
		long start = startDate.getTime();
		long end = endDate.getTime();
		
		return current >= start && current < end;
	}
}
