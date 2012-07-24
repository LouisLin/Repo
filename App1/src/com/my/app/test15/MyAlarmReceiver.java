/**
 * 
 */
package com.my.app.test15;

import com.my.app.test15.lib.MyIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Louis
 *
 */
public class MyAlarmReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getIntExtra(Intent.EXTRA_ALARM_COUNT, 0) > 0) {
			MyIntent.startService(MyBackgroundService.class);
		}

	}

}
