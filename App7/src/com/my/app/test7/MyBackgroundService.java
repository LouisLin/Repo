/**
 * 
 */
package com.my.app.test7;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.xml.sax.SAXException;

import com.my.app.test7.R;
import com.my.app.test7.lib.MyAlertDialog;
import com.my.app.test7.lib.MyApp;
import com.my.app.test7.lib.MyConvert;
import com.my.app.test7.lib.MyDate;
import com.my.app.test7.lib.MyIntent;
import com.my.app.test7.lib.MyLayoutInflater;
import com.my.app.test7.lib.MyNotification;
import com.my.app.test7.lib.MyPendingIntent;
import com.my.app.test7.lib.MyPreferences;
import com.my.app.test7.lib.MyTask;
import com.my.app.test7.lib.MyToast;
import com.my.app.test7.lib.MyWait;
import com.my.app.test7.lib.MyWaitInterface;
import com.my.app.test7.lib.MyTask.OnResultListener;
import com.my.app.test7.lib.MyTask.OnTaskListener;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * @author Louis
 *
 */
public class MyBackgroundService extends Service {

	private Intent mIntent;
	private int mFlags;
	private int mStartId;
	private MyQueryTask mTask;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	/* For long-term Service */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mIntent = intent;
		mFlags = flags;
		mStartId = startId;
//MyToast.show("flags=" + flags + ", startId=" + startId + ", intent=" + intent.getBooleanExtra("polling", false));

		mTask = new MyQueryTask(this,
			new OnTaskListener() {
				
				@Override
				public void onPreExecute() {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onProgressUpdate(Integer value) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPostExecute(Integer result) {
					// TODO Auto-generated method stub
					if (result == 0 && isNeededToNotify()){
						final MyApplication app = ((MyApplication)getApplicationContext());
	
						// Cancel previous notification
						MyNotification.cancel(MyApp.ID);
						
						SharedPreferences preferences = MyPreferences.get();
						boolean sound = preferences.getBoolean("sound", false);
						boolean vibrate = preferences.getBoolean("vibrate", false);
						if (sound && vibrate) {
							MyNotification.notify(MyApp.ID,
								MyNotification.getSoundVibrateNotification());
						} else if (sound) {
							MyNotification.notify(MyApp.ID,
								MyNotification.getSoundNotification());
						} else if (vibrate) {
							MyNotification.notify(MyApp.ID,
								MyNotification.getVibrateNotification());
						} else {
							// Do nothing
						}
	
						View view = MyLayoutInflater.inflate(R.layout.alert);
						TextView yourNum = (TextView)view.findViewById(R.id.textView1);
						yourNum.setText(String.valueOf(app.getQueryRegNo(0)));
						TextView now = (TextView) view.findViewById(R.id.textView2);
						now.setText(String.valueOf(app.getQueryDiagNo(0)));
	
						AlertDialog alert = app.getGlobalAlert();
						if (alert != null) {
							MyAlertDialog.dismiss(alert);
						}
						alert = MyAlertDialog.getDefaultAlertDialogBuilder()
							.setTitle("Progress")
							.setView(view)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									app.setGlobalAlert(null);
									MyIntent.startSingleActivity(MyNotifiedActivity.class);
								}
							})
							.setNeutralButton(null, null)
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									app.setGlobalAlert(null);
									app.stopAlarm();
								}
							})
							.setOnCancelListener(new DialogInterface.OnCancelListener() {
								
								@Override
								public void onCancel(DialogInterface dialog) {
									// TODO Auto-generated method stub
									app.setGlobalAlert(null);
									Notification notification = MyNotification.getDefaultNotification();
									notification.defaults = 0;
									notification.tickerText = null;
									notification.setLatestEventInfo(MyApp.getContext(),
										"XYZ Hospital", "Progress now",
										MyPendingIntent.getActivity(MyNotifiedActivity.class));
						// TODO		.setContentInfo(app.getQueryDiagNo(0) + "/" + app.getQueryRegNo(0))
									/*
									Notification notification = MyNotification.getDefaultNotificationBuilder()
										.setTicker(null)
										.setContentTitle("XYZ Hospital")
										.setContentText("Progress now")
										.setContentInfo(app.getQueryDiagNo(0) + "/" + app.getQueryRegNo(0))
										.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
										.getNotification();
									*/
									MyNotification.notify(MyApp.ID, notification);							
								}
							})
							.create();
						MyAlertDialog.alert(alert);
						app.setGlobalAlert(alert);
					}
					
				}
				
				@Override
				public void onCancelled(Integer result) {
					// TODO Auto-generated method stub
					
				}
			});
		mTask.execute();
		mTask.getResult(10, TimeUnit.SECONDS,
			new OnResultListener() {

				@Override
				public void onResult(Integer result) {
					// TODO Auto-generated method stub
					stopSelfResult(mStartId);
	
				}

			});

		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isNeededToNotify() {
		// TODO Auto-generated method stub
		MyApplication app = ((MyApplication)getApplicationContext());

		int dateDiff = 0;
		try {
			Date dateDiag = app.getQueryDiagDate(0);
			Date dateToday = MyDate.today();
			dateDiff = MyDate.dayDiffence(dateDiag, dateToday);
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
		
		int numDiag = app.getQueryDiagNo(0);
		int numDiff = app.getQueryRegNo(0) - numDiag;
//MyToast.show("dateDiff=" + dateDiff + ", numDiff=" + numDiff);
		app.adjustAlarmInterval(dateDiff, numDiff);

		if (dateDiff < 0) {
			return false;
		}
		if (numDiag <= 0) {
			return false;
		}

		SharedPreferences preferences = MyPreferences.get();
		boolean dayNotify = preferences.getBoolean("day_notify", false);
		if (dayNotify) {
			boolean preceding2 = preferences.getBoolean("day_preceding_2", false);
			if (preceding2 && (dateDiff == 2 || (dateDiff < 2 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;				
			}
			boolean preceding1 = preferences.getBoolean("day_preceding_1", false);
			if (preceding1 && (dateDiff == 1 || (dateDiff < 1 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;				
			}
		}
		boolean numNotify = preferences.getBoolean("num_notify", false);
		if (numNotify) {
			boolean preceding20 = preferences.getBoolean("num_preceding_20", false);
			if (preceding20 && (numDiff == 20 || (numDiff < 20 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;
			}
			boolean preceding10 = preferences.getBoolean("num_preceding_10", false);
			if (preceding10 && (numDiff == 10 || (numDiff < 10 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;
			}
			boolean preceding5 = preferences.getBoolean("num_preceding_5", false);
			if (preceding5 && (numDiff == 5 || (numDiff < 5 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;
			}
			boolean preceding3 = preferences.getBoolean("num_preceding_3", false);
			if (preceding3 && (numDiff == 3 || (numDiff < 3 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;
			}
			boolean preceding1 = preferences.getBoolean("num_preceding_1", false);
			if (preceding1 && (numDiff == 1 || (numDiff < 1 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;
			}
			boolean myturn = preferences.getBoolean("my_turn", false);
			if (myturn && (numDiff == 0 || (numDiff < 0 && !app.hadNotified()))) {
				app.setHadNotified(true);
				return true;
			}
			boolean succeed = preferences.getBoolean("succeeding", false);
			if (succeed && numDiff < 0) {
				app.setHadNotified(true);
				return true;
			}
		}

		return false;
	}

}
