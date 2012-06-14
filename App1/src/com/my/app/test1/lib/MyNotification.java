/**
 * 
 */
package com.my.app.test1.lib;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyNotification {

	public static void notify(Context context) {
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notify = new Notification.Builder(context)
		.setDefaults(Notification.DEFAULT_SOUND)
		.setAutoCancel(true)
		.getNotification();
		manager.notify(1, notify);
	}
}
