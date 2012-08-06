/**
 * 
 */
package com.my.app.test15;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import com.my.app.test15.R;
import com.my.app.test15.lib.MyAlarm;
import com.my.app.test15.lib.MyAlertDialog;
import com.my.app.test15.lib.MyApp;
import com.my.app.test15.lib.MyIntent;
import com.my.app.test15.lib.MyNotification;
import com.my.app.test15.lib.MyPendingIntent;
import com.my.app.test15.lib.MyTask;
import com.my.app.test15.lib.MyToast;
import com.my.app.test15.lib.MyTask.OnTaskListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

	private MyTask mTask;
	private ProgressDialog mProgressDlg;

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
				mTask = new MyQueryTask(MyNotifiedActivity.this,
					new OnTaskListener() {

						@Override
						public void onPreExecute() {
							// TODO Auto-generated method stub
							mProgressDlg = ProgressDialog.show(MyNotifiedActivity.this, null, "Querying...");
						}

						@Override
						public void onProgressUpdate(Integer value) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onPostExecute(Integer result) {
							// TODO Auto-generated method stub
							mProgressDlg.dismiss();								

							if (result == 0) {
								Bundle bundle = new Bundle();
								bundle.putBoolean("polling", true);
								MyIntent.startSingleActivity(MyNotifiedActivity.class, bundle);
							} else {
								AlertDialog alert = MyAlertDialog.getNonActionsAlertDialogBuilder()
									.setMessage("Please try later")
									.create();
								switch (result) {
								case MyQueryTask.EXECUTE_FAILED:
									alert.setTitle("Failed");
									break;
								}
								MyAlertDialog.alert(alert);
							}
						}

						@Override
						public void onCancelled(Integer result) {
							// TODO Auto-generated method stub
							mProgressDlg.dismiss();

							AlertDialog alert = MyAlertDialog.getNonActionsAlertDialogBuilder()
								.setMessage("Timeout\nPlease try later")
								.create();
							MyAlertDialog.alert(alert);
						}
					
					});
				mTask.execute();
				mTask.getResult(10, TimeUnit.SECONDS, null);
			}
	    	
	    });

		Button mainPage = (Button)findViewById(R.id.button1);
		mainPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyNotifiedActivity.this.finish();
				MyIntent.startSingleActivity(MyLauncherActivity.class);

			}
	    	
	    });

	    MyApplication app = ((MyApplication)getApplicationContext());
	    if (!app.isQueryStatusUpdated()) {
	    	finish();
	    }
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (mTask != null) {
			if (mTask.getStatus() != AsyncTask.Status.FINISHED) {
				mProgressDlg = ProgressDialog.show(MyNotifiedActivity.this,
					null, "Registering...");
			}
		}

//	    if (!getIntent().getBooleanExtra("polling", false)) {
	    	MyNotification.cancel(MyApp.ID);
//	    }

		if (getIntent().getBooleanExtra("polling", false)) {
	    	getActionBar().setTitle("QueryStatus");
	    } else {
	    	getActionBar().setTitle("Notification");
	    }

	    MyApplication app = ((MyApplication)getApplicationContext());
		try {
			TextView hospital = (TextView)findViewById(R.id.textView3);
			hospital.setText(app.getQueryHospital(0));
			TextView department = (TextView)findViewById(R.id.textView4);
			department.setText(app.getQueryDepartment(0));
			TextView regDate = (TextView)findViewById(R.id.textView5);
			regDate.setText(app.getDateFormat().format(app.getQueryRegDate(0)));
			TextView diagDate = (TextView)findViewById(R.id.textView6);
			diagDate.setText(app.getDateFormat().format(app.getQueryDiagDate(0)));
			TextView yourNum = (TextView)findViewById(R.id.textView1);
			yourNum.setText(String.valueOf(app.getQueryRegNo(0)));
			TextView now = (TextView)findViewById(R.id.textView2);
			now.setText(String.valueOf(app.getQueryDiagNo(0)));
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
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (mTask != null) {
			mProgressDlg.dismiss();
		}

	    if (!getIntent().getBooleanExtra("polling", false)) {
			MyApplication app = ((MyApplication)getApplicationContext());
	
			Notification notification = MyNotification.getDefaultNotificationBuilder()
				.setDefaults(0)
				.setTicker(null)
				.setContentTitle("XYZ Hospital")
				.setContentText("Progress now")
				.setContentInfo(app.getQueryDiagNo(0) + "/" + app.getQueryRegNo(0))
				.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
				.getNotification();
			MyNotification.notify(MyApp.ID, notification);
	    }
	    
		super.onPause();
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
