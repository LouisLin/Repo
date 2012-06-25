/**
 * 
 */
package com.my.app.test1;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.my.app.test1.lib.MyAlarm;
import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyNotification;
import com.my.app.test1.lib.MyPendingIntent;
import com.my.app.test1.lib.MyToast;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
	    setContentView(R.layout.notified);

	    Button query = (Button)findViewById(R.id.button10);
	    query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putBoolean("polling", true);
				MyIntent.startService(MyBackgroundService.class, bundle);
			}
	    	
	    });

		Button stopAlarm = (Button)findViewById(R.id.button11);
	    stopAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAlarm.cancel(MyPendingIntent.getBroadcast(MyAlarmReceiver.class));

			}
	    	
	    });

        MyNotification.cancel(MyApp.ID);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	    if (getIntent().getBooleanExtra("polling", false)) {
	    	getActionBar().setTitle("QueryStatus");
	    } else {
	    	getActionBar().setTitle("Notification");
	    }

	    MyApplication app = ((MyApplication)getApplicationContext());
		TextView hospital = (TextView)findViewById(R.id.textView3);
		hospital.setText(app.getQueryHospital(0));
		TextView department = (TextView)findViewById(R.id.textView4);
		department.setText(app.getQueryDepartment(0));
		TextView regDate = (TextView)findViewById(R.id.textView5);
		try {
			regDate.setText(app.getDateFormat().format(app.getQueryRegDate(0)));
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView diagDate = (TextView)findViewById(R.id.textView6);
		try {
			diagDate.setText(app.getDateFormat().format(app.getQueryDiagDate(0)));
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView yourNum = (TextView)findViewById(R.id.textView1);
		yourNum.setText(String.valueOf(app.getQueryRegNo(0)));
		TextView now = (TextView)findViewById(R.id.textView2);
		now.setText(String.valueOf(app.getQueryDiagNo(0)));

		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		MyApplication app = ((MyApplication)getApplicationContext());

		Notification notification = MyNotification.getDefaultNotificationBuilder()
			.setDefaults(0)
			.setTicker(null)
			.setContentInfo(app.getQueryDiagNo(0) + "-" + app.getQueryRegNo(0))
			.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
			.getNotification();
		MyNotification.notify(MyApp.ID, notification);

		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		/*
		 * Because this Activity started with FLAG_ACTIVITY_SINGLE_TOP,
		 * so getIntent() will always get the original one.
		 * Inherit onNewIntent() to over-write intent.
		 */
		setIntent(intent);
	}

}
