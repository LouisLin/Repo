/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyIntent;
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
			MyIntent.startService(MyBackgroundService.class);
		}
	}

}
