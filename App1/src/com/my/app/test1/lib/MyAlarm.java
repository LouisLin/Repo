/**
 * 
 */
package com.my.app.test1.lib;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyAlarm {
	public static void setInexactRepeating(long interval, PendingIntent intent) {
		AlarmManager manager = (AlarmManager)MyApp.getContext().getSystemService(Context.ALARM_SERVICE);
		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
			System.currentTimeMillis(), interval, intent);
	}
	
	public static void cancel(PendingIntent intent) {
		AlarmManager manager = (AlarmManager)MyApp.getContext().getSystemService(Context.ALARM_SERVICE);
		manager.cancel(intent);
	}
}
