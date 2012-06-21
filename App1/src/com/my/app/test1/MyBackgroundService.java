/**
 * 
 */
package com.my.app.test1;

import java.net.URI;
import java.net.URISyntaxException;

import com.my.app.test1.lib.MyAlertDialog;
import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyLayoutInflater;
import com.my.app.test1.lib.MyNotification;
import com.my.app.test1.lib.MyPendingIntent;
import com.my.app.test1.lib.MyToast;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

/**
 * @author Louis
 *
 */
public class MyBackgroundService extends Service {

	private class MyTask extends AsyncTask<URI, Integer, Long> {

		/*
		 * This step is normally used to setup the task,
		 * for instance by showing a progress bar in the user interface.
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			MyToast.show("AsyncTask:onPreExecute()");
			super.onPreExecute();
		}

		/*
		 * This step is used to perform background computation
		 * that can take a long time.
		 * This step can also use publishProgress(Progress...)
		 * to publish one or more units of progress.
		 */
		@Override
		protected Long doInBackground(URI... params) {
			// TODO Auto-generated method stub
			if (!isCancelled()) {
				/* TODO: check server */
				publishProgress(100);
			}

			return (long) 999;
		}

		/*
		 * This method is used to display any form of progress
		 * in the user interface while the background computation
		 * is still executing.
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			MyToast.show("AsyncTask:onProgressUpdate()");

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			MyToast.show("AsyncTask:onPostExecute()");

			View view = MyLayoutInflater.inflate(R.layout.alert);
			TextView yourNum = (TextView) view.findViewById(R.id.textView1);
			yourNum.setText("39");
			TextView now = (TextView) view.findViewById(R.id.textView2);
			now.setText("16");
			AlertDialog alert = MyAlertDialog.getDefaultAlertDialogBuilder()
				.setMessage("Progress")
				.setView(view)
				.create();
			MyAlertDialog.alert(alert);

			Notification notification = MyNotification.getDefaultNotificationBuilder()
				.setNumber(39)
				.setContentIntent(MyPendingIntent.getActivity(MyNotifiedActivity.class))
				.getNotification();
			MyNotification.notify(MyApp.ID, notification);

			SystemClock.sleep(3000);

			MyBackgroundService.this.stopSelf();
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(Long result) {
			// TODO Auto-generated method stub
			MyToast.show("AsyncTask:onCancelled()");
			super.onCancelled(result);
		}

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		/* For One-Shot Service */
		MyToast.show(MyBackgroundService.class.getSimpleName() + ":onCreate()");
		try {
			new MyTask().execute(new URI("https://a.com"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* For long-term Service */
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		// TODO Auto-generated method stub
//		MyToast.show(this, "flags=" + flags + ", startId=" + startId);
//		try {
//			new MyTask().execute(new URI("https://a.com"));
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return super.onStartCommand(intent, flags, startId);
//	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		MyToast.show(MyBackgroundService.class.getSimpleName() + ":onDestroy()");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
