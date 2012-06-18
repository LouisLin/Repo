package com.my.app.test5;

import com.my.app.test5.lib.MyNotification;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class App5Activity extends Activity {
    private OnClickListener mNotifyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PendingIntent intent = PendingIntent.getActivity(App5Activity.this, 0,
					new Intent(App5Activity.this, App5Activity.class), 0);
			Notification.Builder builder = MyNotification.getDefaultNotificationBuilder(App5Activity.this);
			builder
//			.setDefaults(Notification.DEFAULT_VIBRATE)
//				.setTicker("MyTicker")
//				.setSmallIcon(R.drawable.ic_launcher)	// important
				.setContentTitle("MyTitle")
				.setContentText("MyText")
//				.setContentInfo("MyInfo")
				.setNumber(39)
				.setContentIntent(intent)
//				.setAutoCancel(true)
				;

			MyNotification.notify(App5Activity.this, R.string.app_name, builder.getNotification());
			
			AlertDialog alert = new AlertDialog();
		}
    	
    };

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        Button notify = (Button)findViewById(R.id.button1);
        notify.setOnClickListener(mNotifyListener);
    }
}