/**
 * 
 */
package com.my.app.test1.lib;

import com.my.app.test1.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyNotification {

	public static Notification.Builder getDefaultNotificationBuilder() {
		return new Notification.Builder(MyApp.getContext())
		.setDefaults(Notification.DEFAULT_ALL)
		.setSmallIcon(R.drawable.ic_launcher)	// important
		.setTicker(MyApp.getName())
		.setContentTitle(MyApp.getName())
		.setContentText("Description")
//		.setContentInfo("Info")
//		.setNumber(0)
//		.setContentIntent(intent)
//		.setOnlyAlertOnce(false)
		.setAutoCancel(true)
		;
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
