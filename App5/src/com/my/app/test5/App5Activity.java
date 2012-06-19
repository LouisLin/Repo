package com.my.app.test5;

import com.my.app.test5.lib.MyAlertDialog;
import com.my.app.test5.lib.MyLayoutInflater;
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
import android.widget.TextView;

public class App5Activity extends Activity {
    private OnClickListener mNotifyListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			PendingIntent intent = PendingIntent.getActivity(App5Activity.this, 0,
					new Intent(App5Activity.this, NotifiedActivity.class), Intent.FLAG_ACTIVITY_NEW_TASK);
			Notification notification = MyNotification.getDefaultNotificationBuilder(App5Activity.this)
				.setNumber(39)
				.setContentIntent(intent)
				.getNotification();
			MyNotification.notify(App5Activity.this, R.string.app_name, notification);
			
			View view = MyLayoutInflater.inflate(App5Activity.this, R.layout.alert);
			TextView yourNum = (TextView) view.findViewById(R.id.textView1);
			yourNum.setText("39");
			TextView now = (TextView) view.findViewById(R.id.textView2);
			now.setText("16");
			AlertDialog alert = MyAlertDialog.getDefaultAlertDialogBuilder(App5Activity.this)
				.setMessage("看診進度通知")
				.setView(view)
				.create();
			MyAlertDialog.alert(App5Activity.this, alert);
		}
    	
    };
	private OnClickListener mCancelListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			MyNotification.cancel(App5Activity.this, R.string.app_name);
		}
		
	};

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        Button notify = (Button)findViewById(R.id.button1);
        notify.setOnClickListener(mNotifyListener);
        
        Button cancel = (Button)findViewById(R.id.button2);
        cancel.setOnClickListener(mCancelListener );
    }
}