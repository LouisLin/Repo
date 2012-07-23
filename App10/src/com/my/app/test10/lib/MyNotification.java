/**
 * 
 */
package com.my.app.test10.lib;

import com.my.app.test10.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyNotification {

	public static Notification getDefaultNotification() {
		Notification notify = new Notification();
		notify.defaults = Notification.DEFAULT_ALL;
		notify.icon = R.drawable.ic_launcher;
		notify.tickerText = MyApp.getName();
		notify.setLatestEventInfo(MyApp.getContext(), MyApp.getName(), "Description", null);
		return notify;
	}
	/*
	public static Notification.Builder getDefaultNotificationBuilder() {
		return new Notification.Builder(MyApp.getContext())
			.setDefaults(Notification.DEFAULT_ALL)
			.setSmallIcon(R.drawable.ic_launcher)	// important
			.setTicker(MyApp.getName())
			.setContentTitle(MyApp.getName())
			.setContentText("Description")
//			.setContentInfo("Info")
//			.setNumber(0)
//			.setContentIntent(intent)
			.setOnlyAlertOnce(true)
			.setAutoCancel(true)
			;
	}
	*/
	
	public static Notification getSoundVibrateNotification() {
		Notification notify = new Notification();
		notify.defaults = Notification.DEFAULT_ALL;
		return notify;
		/*
		return new Notification.Builder(MyApp.getContext())
			.setDefaults(Notification.DEFAULT_ALL)
//			.setOnlyAlertOnce(true)
			.getNotification();
		*/
	}

	public static Notification getSoundNotification() {
		Notification notify = new Notification();
		notify.defaults = Notification.DEFAULT_SOUND;
		return notify;
		/*
		return new Notification.Builder(MyApp.getContext())
			.setDefaults(Notification.DEFAULT_SOUND)
//			.setOnlyAlertOnce(true)
			.getNotification();
		*/
	}

	public static Notification getVibrateNotification() {
		Notification notify = new Notification();
		notify.defaults = Notification.DEFAULT_VIBRATE;
		return notify;
		/*
		return new Notification.Builder(MyApp.getContext())
			.setDefaults(Notification.DEFAULT_VIBRATE)
//			.setOnlyAlertOnce(true)
			.getNotification();
		*/
	}

	public static void notify(int id, Notification notification) {
		NotificationManager manager = (NotificationManager)MyApp.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(id, notification);
	}

	public static void cancel(int id) {
		NotificationManager manager = (NotificationManager)MyApp.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(id);		
	}
}
