/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyToast;

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
		if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			MyIntent.startService(context, MyBootService.class);
		} else if (intent.getIntExtra(Intent.EXTRA_ALARM_COUNT, 0) > 0) {
			MyToast.show(context, "ALARM_COUNT > 0");
			MyIntent.startService(context, MyBootService.class);
		}
	}

}
