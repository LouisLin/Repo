/**
 * 
 */
package com.my.app.test1.lib;

import android.app.PendingIntent;
import android.content.Intent;

/**
 * @author Louis
 *
 */
public class MyPendingIntent {

	public static PendingIntent getActivity(Class<?> cls) {
		return PendingIntent.getActivity(MyApp.getContext(), 0,
			new Intent(MyApp.getContext(), cls),
			Intent.FLAG_ACTIVITY_NEW_TASK);
	}
}
