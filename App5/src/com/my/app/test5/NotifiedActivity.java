/**
 * 
 */
package com.my.app.test5;

import com.my.app.test5.lib.MyNotification;
import com.my.app.test5.lib.MyToast;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Louis
 *
 */
public class NotifiedActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
        setContentView(R.layout.main2);
        
        MyNotification.cancel(this, R.string.app_name);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		PendingIntent intent = PendingIntent.getActivity(this, 0,
				new Intent(this, NotifiedActivity.class), Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification notification = MyNotification.getDefaultNotificationBuilder(this)
			.setDefaults(0)
			.setTicker(null)
			.setNumber(39)
			.setContentIntent(intent)
			.getNotification();
		MyNotification.notify(this, R.string.app_name, notification);

		super.onDestroy();
	}

}
