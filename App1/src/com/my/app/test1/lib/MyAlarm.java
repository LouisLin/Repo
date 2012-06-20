/**
 * 
 */
package com.my.app.test1.lib;

import com.my.app.test1.MyApp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyAlarm {
	public static void setInexactRepeating(
		Context _context, long interval, PendingIntent intent) {
		Context context = MyApp.getContext();
		AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
			System.currentTimeMillis(), interval, intent);
	}
	
	public static void cancel(Context context, PendingIntent intent) {
		AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		manager.cancel(intent);
	}
}
