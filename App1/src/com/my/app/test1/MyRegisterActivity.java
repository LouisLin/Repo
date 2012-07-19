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
import com.my.app.test1.lib.MyWait;
import com.my.app.test1.lib.MyWaitInterface;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * @author Louis
 *
 */
public class MyRegisterActivity extends Activity {

	private class MyRegTask extends AsyncTask<String, Integer, Integer> {
		public ProgressDialog mProgressDlg = null;
		private	Exception mException = null;

		/*
		 * This step is normally used to setup the task,
		 * for instance by showing a progress bar in the user interface.
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mProgressDlg = ProgressDialog.show(MyRegisterActivity.this,
				"Registeration", "Please wait while registering");

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
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			}
			try {
				Thread.sleep(mTEST_regTime);
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
					app.updateRegisterStatus(resp.getEntity().getContent());
					result = app.getRegisterError();
				}
				resp.getEntity().consumeContent();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mException = e;
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
			mProgressDlg.dismiss();
			
			if (result == 0) {
				MyPreferences.setBoolean(MyApplication.PREF_REGISTERED, true);
				MyPreferences.setString(MyApplication.PREF_ISDN,
					MyRegisterActivity.this.mPhoneNum.getText().toString());

				if (!MyRegisterActivity.this.isFinishing()) {
					AlertDialog alert = MyAlertDialog.getNonActionsAlertDialogBuilder()
						.setMessage("Register OK!")
						.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
							@Override
							public void onCancel(DialogInterface dialog) {
								// TODO Auto-generated method stub
								MyIntent.startActivity(MyLauncherActivity.class);
							}
						})
						.create();
					MyAlertDialog.alert(alert);
					MyRegisterActivity.this.finish();
				}

				MyApplication app = ((MyApplication)getApplicationContext());
				app.startAlarm(MyApplication.STARTUP_DELAY);
			} else {
				assert(mException != null);
//				MyToast.show("result=" + result);
				AlertDialog alert = MyAlertDialog.getNonActionsAlertDialogBuilder()
						.setTitle("Registeration Failed")
						.setMessage(mException.getLocalizedMessage())
						.create();
				MyAlertDialog.alert(alert);
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(Integer result) {
			// TODO Auto-generated method stub
			mProgressDlg.dismiss();

			AlertDialog alert = MyAlertDialog.getNonActionsAlertDialogBuilder()
					.setTitle("Timeout")
					.setMessage("Please try again")
					.create();
			MyAlertDialog.alert(alert);

			super.onCancelled(result);
		}

	}

	private EditText mPhoneNum;
	private EditText mName;
	static private MyRegTask mTask = null;
	
	private long mTEST_regTime = 3000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
		setContentView(R.layout.register);
		
		mPhoneNum = (EditText)findViewById(R.id.editText1);
		String isdn = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		if (!isdn.isEmpty()) {
			mPhoneNum.setText(isdn);
		}
		mName = (EditText)findViewById(R.id.editText2);

		Button register = (Button)findViewById(R.id.button1);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String isdn = mPhoneNum.getText().toString();
				String name = mName.getText().toString();
				if (isdn.isEmpty() || name.isEmpty()) {
					return;
				}
				String serviceURI = getResources().getString(R.string.register_uri);
				InputStream in = getResources().openRawResource(R.raw.register);
//				String sn = Build.SERIAL;
				String imei = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
				String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
				String registerXml = String.format(
					MyConvert.inputStream2String(in),
					imei, imsi, isdn, name, "", "");
				mTask = (MyRegTask)new MyRegTask().execute(serviceURI, registerXml);
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
			}
	    	
	    });

		ToggleButton regTime = (ToggleButton)findViewById(R.id.toggleButton1);
		regTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (mTask != null) {
			if (mTask.getStatus() != AsyncTask.Status.FINISHED) {
				mTask.mProgressDlg = ProgressDialog.show(MyRegisterActivity.this,
					"Registeration", "Please wait while registering");
			}
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (mTask != null) {
			mTask.mProgressDlg.dismiss();
		}
		
		super.onStop();
	}

}
