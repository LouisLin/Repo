package com.my.app.test10.lib;

import java.util.Calendar;
import java.util.Date;

public class MyDate {
	final public static long MS_A_DAY = 24 * 60 * 60 * 1000;

	public static Date now() {
		return new Date();
	}

	public static Date today() {
		return truncateToDate(new Date());
	}

	public static Date daysBefore(int before) {
		Date date = today();
		long ms = date.getTime() - before * MS_A_DAY;
		date.setTime(ms);
		return date;
	}
	
	public static Date daysAfter(int after) {
		Date date = today();
		long ms = date.getTime() + after * MS_A_DAY;
		date.setTime(ms);
		return date;
	}

	public static Date yesterday() {
		return daysBefore(1);
	}
	
	public static Date tomorrow() {
		return daysAfter(1);
	}

	public static Date truncateToDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static int dayDiffence(Date date1, Date date2) {
		long ms1 = truncateToDate(date1).getTime();
		long ms2 = truncateToDate(date2).getTime();
		return (int)((ms1 - ms2) / MS_A_DAY);
	}
}
