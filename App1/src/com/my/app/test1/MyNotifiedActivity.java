/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyNotification;
import com.my.app.test1.lib.MyPendingIntent;
import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;

/**
 * @author Louis
 *
 */
public class MyNotifiedActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.progress);

        MyNotification.cancel(MyApp.ID);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Notification notification = MyNotification.getDefaultNotificationBuilder()
			.setDefaults(0)
			.setTicker(null)
			.setNumber(39)
			.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
			.getNotification();
		MyNotification.notify(MyApp.ID, notification);

		super.onDestroy();
	}

}
