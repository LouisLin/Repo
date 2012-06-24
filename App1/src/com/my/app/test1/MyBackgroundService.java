/**
 * 
 */
package com.my.app.test1;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

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
import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyLayoutInflater;
import com.my.app.test1.lib.MyNotification;
import com.my.app.test1.lib.MyPendingIntent;
import com.my.app.test1.lib.MyPreferences;
import com.my.app.test1.lib.MyToast;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
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

	private class MyTask extends AsyncTask<String, Integer, Integer> {

		/*
		 * This step is normally used to setup the task,
		 * for instance by showing a progress bar in the user interface.
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			boolean numNotify = MyPreferences.getBoolean("num_notify", false);
			boolean succeed = MyPreferences.getBoolean("succeeding", false);
			boolean preceding1 = MyPreferences.getBoolean("num_preceding_1", false);
			boolean preceding3 = MyPreferences.getBoolean("num_preceding_3", false);
			boolean preceding5 = MyPreferences.getBoolean("num_preceding_5", false);
			boolean preceding10 = MyPreferences.getBoolean("num_preceding_10", false);
			boolean preceding20 = MyPreferences.getBoolean("num_preceding_20", false);

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
			publishProgress(100);

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
				final MyApplication app = ((MyApplication)getApplicationContext());

				MyNotification.notify(MyApp.ID, MyNotification.getSoundVibrateNotification());

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
						}
					})
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							app.setGlobalAlert(null);
							Notification notification = MyNotification.getDefaultNotificationBuilder()
								.setTicker(null)
								.setContentInfo(app.getQueryDiagNo(0) + "-" + app.getQueryRegNo(0))
								.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
								.getNotification();
							MyNotification.notify(MyApp.ID, notification);							
						}
					})
					.create();
				MyAlertDialog.alert(alert);
				app.setGlobalAlert(alert);
			} else {
				MyToast.show("result=" + result);
			}

			MyBackgroundService.this.stopSelf();
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(Integer result) {
			// TODO Auto-generated method stub
			MyToast.show("AsyncTask:onCancelled()");

			MyBackgroundService.this.stopSelf();
			super.onCancelled(result);
		}

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		/* For One-Shot Service */
		String serviceURI = getResources().getString(R.string.query_uri);
		InputStream in = getResources().openRawResource(R.raw.query);
		String sn = Build.SERIAL;
		String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		String isdn = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		if (isdn == "") {
			isdn = MyPreferences.getString("isdn", "");
		}
		String queryXml = String.format(MyConvert.inputStream2String(in), sn, imsi, isdn);

		AsyncTask<String, Integer, Integer> task =
			new MyTask().execute(serviceURI, queryXml);
//		if (task.getStatus() != AsyncTask.Status.FINISHED) {
//			task.cancel(true);
//		}
	}

	/* For long-term Service */
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		// TODO Auto-generated method stub
//		MyToast.show(this, "flags=" + flags + ", startId=" + startId);
//		AsyncTask<String, Integer, Integer> task =
//				new MyTask().execute(serviceURI, queryXml);
//
//		return super.onStartCommand(intent, flags, startId);
//	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
