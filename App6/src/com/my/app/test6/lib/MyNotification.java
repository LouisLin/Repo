/**
 * 
 */
package com.my.app.test6.lib;

import com.my.app.test6.MyApp;
import com.my.app.test6.R;

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
		String appName = context.getString(R.string.app_name);
		return new Notification.Builder(context)
		.setDefaults(Notification.DEFAULT_VIBRATE)
		.setSmallIcon(R.drawable.ic_launcher)	// important
		.setTicker(appName)
		.setContentTitle(appName)
		.setContentText("Description")
//		.setContentInfo("Info")
//		.setNumber(0)
//		.setContentIntent(intent)
		.setOnlyAlertOnce(true)
		.setAutoCancel(true)
		;
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
	
	public static void notify(Context _context, int id, Notification notification) {
		Context context = MyApp.getContext();
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(id, notification);
	}

	public static void cancel(Context context, int id) {
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(id);		
	}
}
