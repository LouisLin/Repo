/**
 * 
 */
package com.my.app.test1;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Louis
 *
 */
public class MyRegisterActivity extends Activity {

	private class MyTask extends AsyncTask<String, Integer, Integer> {

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

			InputStream in = getResources().openRawResource(R.raw.reg_status);
			if (isCancelled()) return result;
			try {
				app.updateRegisterStatus(in);
				result = app.getRegisterError(); 
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
					app.updateRegisterStatus(resp.getEntity().getContent());
					result = app.getRegisterError();
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
				MyPreferences.setBoolean(MyApplication.PREF_REGISTERED, true);
				MyPreferences.setString(MyApplication.PREF_ISDN,
					MyRegisterActivity.this.mPhoneNum.getText().toString());

				AlertDialog alert = MyAlertDialog.getDefaultAlertDialogBuilder()
					.setMessage("Register OK!")
					.create();
				MyAlertDialog.alert(alert);
				MyRegisterActivity.this.finish();
			} else {
				MyToast.show("result=" + result);
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(Integer result) {
			// TODO Auto-generated method stub
			MyToast.show("AsyncTask:onCancelled()");

			super.onCancelled(result);
		}

	}

	private EditText mPhoneNum;
	private EditText mName;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
		setContentView(R.layout.register);
		
		mPhoneNum = (EditText)findViewById(R.id.editText1);
		String isdn = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		if (isdn != "") {
			mPhoneNum.setText(isdn);
		}
		mName = (EditText)findViewById(R.id.editText2);

		Button register = (Button)findViewById(R.id.button1);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String serviceURI = getResources().getString(R.string.register_uri);
				InputStream in = getResources().openRawResource(R.raw.register);
//				String sn = Build.SERIAL;
				String imei = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
				String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
				String isdn = mPhoneNum.getText().toString();
				String name = mName.getText().toString();
				String registerXml = String.format(
					MyConvert.inputStream2String(in),
					imei, imsi, isdn, name, "", "");
				AsyncTask<String, Integer, Integer> task =
					new MyTask().execute(serviceURI, registerXml);
			}
	    	
	    });

	}

}
