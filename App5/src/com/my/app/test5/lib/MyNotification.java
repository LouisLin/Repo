/**
 * 
 */
package com.my.app.test5.lib;

import com.my.app.test5.App5Activity;
import com.my.app.test5.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Louis
 *
 */
public class MyNotification {

	public static Notification.Builder getDefaultNotificationBuilder(Context context) {
		return new Notification.Builder(context)
		.setDefaults(Notification.DEFAULT_ALL)
		.setTicker(context.getPackageName())
		.setSmallIcon(R.drawable.ic_launcher)	// important
		.setContentTitle("MyTitle")
		.setContentText("MyText")
		.setContentInfo("MyInfo")
//		.setNumber(0)
//		.setContentIntent(intent)
//		.setAutoCancel(true)
		;
	}

	public static void notify(Activity activity, int id, Notification notification) {
		NotificationManager manager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(id, notification);
	}

/*	public static void notify(Activity activity, int id) {
		PendingIntent intent = PendingIntent.getActivity(activity, 0,
			new Intent(activity, activity.getClass()), 0);

		Notification notification = new Notification.Builder(activity)
		.setDefaults(Notification.DEFAULT_VIBRATE)
		.setTicker(activity.getPackageName())
//		.setSmallIcon(R.drawable.ic_launcher)	// important
		.setContentTitle("MyTitle")
		.setContentText("MyText")
//		.setContentInfo("MyInfo")
		.setNumber(39)
		.setContentIntent(intent)
//		.setAutoCancel(true)
		.getNotification();
		notification.

		notify(activity, id, notification);
	}
*/
	
}
