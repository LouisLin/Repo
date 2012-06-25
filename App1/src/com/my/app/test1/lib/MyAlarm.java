/**
 * 
 */
package com.my.app.test1.lib;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

/**
 * @author Louis
 *
 */
public class MyAlarm {
	public static void set(long delay, PendingIntent intent) {
		AlarmManager manager = (AlarmManager)MyApp.getContext().getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.RTC_WAKEUP,
			System.currentTimeMillis() + delay, intent);
	}

	public static void setInexactRepeating(long delay, long interval, PendingIntent intent) {
		MyToast.showLong("delay=" + delay + ", interval=" + interval);
		AlarmManager manager = (AlarmManager)MyApp.getContext().getSystemService(Context.ALARM_SERVICE);
		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
			System.currentTimeMillis() + delay, interval, intent);
	}
	
	public static void cancel(PendingIntent intent) {
		AlarmManager manager = (AlarmManager)MyApp.getContext().getSystemService(Context.ALARM_SERVICE);
		manager.cancel(intent);
	}
}
