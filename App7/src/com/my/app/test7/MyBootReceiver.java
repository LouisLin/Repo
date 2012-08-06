/**
 * 
 */
package com.my.app.test7;

import com.my.app.test7.lib.MyApp;
import com.my.app.test7.lib.MyIntent;
import com.my.app.test7.lib.MyPreferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Louis
 *
 */
public class MyBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		if (intent.getAction() != null &&
			intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			boolean registered = MyPreferences.getBoolean(MyApplication.PREF_REGISTERED, false);
			if (registered) {
				MyApplication app = (MyApplication)context.getApplicationContext();
				app.startAlarm(MyApplication.STARTUP_DELAY);
			}
		}
	}

}
