/**
 * 
 */
package com.my.app.test1;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.xml.sax.SAXException;

import com.my.app.test1.lib.MyAlertDialog;
import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyConvert;
import com.my.app.test1.lib.MyDate;
import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyLayoutInflater;
import com.my.app.test1.lib.MyNotification;
import com.my.app.test1.lib.MyPendingIntent;
import com.my.app.test1.lib.MyPreferences;
import com.my.app.test1.lib.MyToast;
import com.my.app.test1.lib.MyWait;
import com.my.app.test1.lib.MyWaitInterface;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
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
import android.provider.CalendarContract.Calendars;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

/**
 * @author Louis
 *
 */
public class MyBackgroundService extends Service {

	private class MyQueryTask extends AsyncTask<String, Integer, Integer> {

		/*
		 * This step is normally used to setup the task,
		 * for instance by showing a progress bar in the user interface.
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
		}

		/*
		 * This step is used to perform background computation
		 * that can take a long time.
		 * This step can also use publishProgress(Progress...)
		 * to publish one or more units of progress.
		 */
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			int result = -1;

			if (isCancelled()) return result;
			MyApplication app = ((MyApplication)getApplicationContext());

			InputStream in = getResources().openRawResource(R.raw.query_status);
			if (isCancelled()) return result;
			try {
				app.updateQueryStatus(in);
				result = app.getQueryError(); 
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			URI uri = null;
			try {
				uri = new URI(params[0]);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return result;
			}

			HttpPost req = new HttpPost(uri);
			try {
				StringEntity entify = new StringEntity(params[1], HTTP.UTF_8);
				entify.setContentType("text/xml");
		        req.setEntity(entify);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return result;
			}

			try {
				HttpResponse resp = new DefaultHttpClient().execute(req);
				if (isCancelled()) return result;
				int status = resp.getStatusLine().getStatusCode();
				if (status == 200) {
					app.updateQueryStatus(resp.getEntity().getContent());
					result = app.getQueryError();
				}
				resp.getEntity().consumeContent();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			publishProgress(10000);

			return result;
		}

		/*
		 * This method is used to display any form of progress
		 * in the user interface while the background computation
		 * is still executing.
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if (result == 0) {
				if (MyBackgroundService.this.mIntent.getBooleanExtra("polling", false)) {
					Bundle bundle = new Bundle();
					bundle.putBoolean("polling", true);
					MyIntent.startSingleActivity(MyNotifiedActivity.class, bundle);					
				} else if (MyBackgroundService.this.isNeededToNotify()){
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
						.setMessage("Progress")
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
								Notification notification = MyNotification.getDefaultNotificationBuilder()
									.setTicker(null)
									.setContentTitle("XXX Hospital")
									.setContentText("Progress now")
									.setContentInfo(app.getQueryDiagNo(0) + "/" + app.getQueryRegNo(0))
									.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
									.getNotification();
								MyNotification.notify(MyApp.ID, notification);							
							}
						})
						.create();
					MyAlertDialog.alert(alert);
					app.setGlobalAlert(alert);
				}
			} else {
				MyToast.show("result=" + result);
			}

			MyBackgroundService.this.stopSelfResult(mStartId);
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(Integer result) {
			// TODO Auto-generated method stub
			MyToast.show("Timeout, application will try later.");

			MyBackgroundService.this.stopSelfResult(mStartId);
			super.onCancelled(result);
		}

	}

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

		String serviceURI = getResources().getString(R.string.query_uri);
		InputStream in = getResources().openRawResource(R.raw.query);
//		String sn = Build.SERIAL;
		String imei = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		String isdn = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		if (isdn == "") {
			isdn = MyPreferences.getString(MyApplication.PREF_ISDN, "");
		}
		String queryXml = String.format(MyConvert.inputStream2String(in), imei, imsi, isdn);

		mTask = (MyQueryTask)new MyQueryTask().execute(serviceURI, queryXml);
		MyWait.wait(10,
			new MyWaitInterface.OnCompareListener() {
				
				@Override
				public boolean isDone() {
					// TODO Auto-generated method stub
					return (mTask.getStatus() == AsyncTask.Status.FINISHED);
				}
			},
			new MyWaitInterface.OnTimeoutListener() {
				
				@Override
				public void cancel() {
					// TODO Auto-generated method stub
					mTask.cancel(true);
				}
			});

		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (mTask.getStatus() != AsyncTask.Status.FINISHED) {
			mTask.cancel(true);
		}

		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNeededToNotify() {
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
