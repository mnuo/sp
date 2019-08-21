/**
 * 
 */
package com.hwagain.system.util;

/**
 * @author mon
 *
 */
public class ConstantUtil {
	public static final String APP_ID = ""; 
	public static final String APP_SECRET = "";
	//早餐时间段
	public static String[] BREAKFAST = {"06:00", "09:00"};
	//午餐时间段
	public static String[] LUNCH = {"09:00", "14:00"};
	//晚餐时间段
	public static String[] DINNER = {"14:00", "18:00"};
	
	//18点夜宵时间段
	public static String[] MIDNIGHT_18 = {"18:00", "20:00"};
	//2点夜宵时间段
	public static String[] MIDNIGHT_02 = {"21:00", "02:00"};
	
	public static String getMealType(String date, String day){
		try {
			if(DateUtil.hourMinuteBetween(date, day + " " + BREAKFAST[0], day + " " + BREAKFAST[1])){
				return "1002";//早餐
			}
			if(DateUtil.hourMinuteBetween(date, day + " " + LUNCH[0], day + " " + LUNCH[1])){
				return "1003";//午餐
			}
			if(DateUtil.hourMinuteBetween(date, day + " " + DINNER[0], day + " " + DINNER[1])){
				return "1004";//晚餐
			}
			if(DateUtil.hourMinuteBetween(date, day + " " + MIDNIGHT_18[0], day + " " + MIDNIGHT_18[1])){
				return "1005";//18点夜宵
			}
			String nextDay = DateUtil.getNextDay(day, 1);
			if(DateUtil.hourMinuteBetween(date, date + " " + MIDNIGHT_18[0], nextDay + " " + MIDNIGHT_18[1])){
				return "1001";//18点夜宵
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static final String REDIS_IDENTIFYING_CODE= "订餐系统验证码:";
	public static final String REDIS_SESSION = "订餐系统用户:";
	public static final String REDIS_TOKEN = "订餐系统用户token:";
	public static final String REDIS_MANAGE_TOKEN = "订餐系统管理用户token:";
	
	//session过期时间 5小时
	public static final int REDIS_EXPIRE = 10;
	public static final int REDIS_EXPIRE_DAYS = 5;
	
}
