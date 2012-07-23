package com.my.app.test10;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CancellationException;
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

import com.my.app.test10.lib.MyAlertDialog;
import com.my.app.test10.lib.MyApp;
import com.my.app.test10.lib.MyConvert;
import com.my.app.test10.lib.MyIntent;
import com.my.app.test10.lib.MyLayoutInflater;
import com.my.app.test10.lib.MyNotification;
import com.my.app.test10.lib.MyPendingIntent;
import com.my.app.test10.lib.MyPreferences;
import com.my.app.test10.lib.MyTask;
import com.my.app.test10.lib.MyToast;
import com.my.app.test10.lib.MyTask.DoInBackgroundCallback;
import com.my.app.test10.lib.MyTask.OnTaskListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

public class MyQueryTask extends MyTask {

	private Context mContext;
//	private OnTaskListener mTaskListener = null;

	public MyQueryTask(Context context, OnTaskListener listener) {
		super(null, listener);

		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public MyQueryTask(Context context) {
		super();
	}

	public void SetOnTaskListener(OnTaskListener listener) {
		super.SetOnTaskListener(null, listener);
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
		int result = 0;

		if (isCancelled()) return CANCELLED;
		MyApplication app = ((MyApplication)mContext.getApplicationContext());

		if (MyPreferences.getBoolean(MyApplication.TEST_PREF_LOCAL_QUERY, false)) {
			InputStream in = mContext.getResources().openRawResource(R.raw.query_status);
			if (isCancelled()) return CANCELLED;
			try {
				app.updateQueryStatus(in);
				result = app.getQueryError(); 
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			}
			try {
				if (MyPreferences.getBoolean(MyApplication.TEST_PREF_QUERY_TIMEOUT, false)) {
					Thread.sleep(30000);
				} else {
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return TIMEOUT;
			}
			if (isCancelled()) return CANCELLED;
		} else {
			HttpPost req = new HttpPost(app.getQueryURI());
			try {
				StringEntity entify = new StringEntity(app.getQueryXml(), HTTP.UTF_8);
				entify.setContentType("text/xml");
		        req.setEntity(entify);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			}
	
			try {
				HttpResponse resp = new DefaultHttpClient().execute(req);
				if (isCancelled()) return CANCELLED;
				int status = resp.getStatusLine().getStatusCode();
				if (status == 200) {
					app.updateQueryStatus(resp.getEntity().getContent());
					result = app.getQueryError();
				}
				resp.getEntity().consumeContent();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return EXECUTE_FAILED;
			}
		}
		publishProgress(10000);

		return result;
	}

}
