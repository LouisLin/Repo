/**
 * 
 */
package com.my.app.test15;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.xml.sax.SAXException;

import com.my.app.test15.R;
import com.my.app.test15.lib.MyAlertDialog;
import com.my.app.test15.lib.MyApp;
import com.my.app.test15.lib.MyConvert;
import com.my.app.test15.lib.MyIntent;
import com.my.app.test15.lib.MyLayoutInflater;
import com.my.app.test15.lib.MyNotification;
import com.my.app.test15.lib.MyPendingIntent;
import com.my.app.test15.lib.MyPreferences;
import com.my.app.test15.lib.MyTask;
import com.my.app.test15.lib.MyToast;
import com.my.app.test15.lib.MyWait;
import com.my.app.test15.lib.MyWaitInterface;
import com.my.app.test15.lib.MyTask.DoInBackgroundCallback;
import com.my.app.test15.lib.MyTask.OnTaskListener;

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

	private EditText mPhoneNum;
	private EditText mName;
	private MyTask mTask;
	private ProgressDialog mProgressDlg;

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
				final String isdn = mPhoneNum.getText().toString();
				final String name = mName.getText().toString();
				if (isdn.isEmpty()) {
					MyToast.show("Please fill your phone number");
					return;
				}
				if (name.isEmpty()) {
					MyToast.show("Please fill your name");
					return;
				}

				mTask = new MyTask(
					new DoInBackgroundCallback() {

						@Override
						public Integer doInBackground(String... params) {
							// TODO Auto-generated method stub
							Integer result = 0;

							if (mTask.isCancelled()) return MyTask.CANCELLED;
							MyApplication app = ((MyApplication)getApplicationContext());

							if (MyPreferences.getBoolean(MyApplication.TEST_PREF_LOCAL_REGISTER, false)) {
								InputStream in = getResources().openRawResource(R.raw.reg_status);
								if (mTask.isCancelled()) return MyTask.CANCELLED;
								try {
									app.updateRegisterStatus(in);
									result = app.getRegisterError(); 
								} catch (ParserConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								} catch (SAXException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								}
								try {
									if (MyPreferences.getBoolean(MyApplication.TEST_PREF_REGISTER_TIMEOUT, false)) {
										Thread.sleep(30000);
									} else {
										Thread.sleep(3000);
									}
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.TIMEOUT;
								}
								if (mTask.isCancelled()) return MyTask.CANCELLED;
							} else {
								HttpPost req = new HttpPost(app.getRegisterURI());
								try {
									StringEntity entify = new StringEntity(
										app.getRegisterXml(isdn, name),
										HTTP.UTF_8);
									entify.setContentType("text/xml");
							        req.setEntity(entify);
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								}
	
								try {
									HttpResponse resp = new DefaultHttpClient().execute(req);
									if (mTask.isCancelled()) return MyTask.CANCELLED;
									int status = resp.getStatusLine().getStatusCode();
									if (status == 200) {
										app.updateRegisterStatus(resp.getEntity().getContent());
										result = app.getRegisterError();
									}
									resp.getEntity().consumeContent();
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								} catch (IllegalStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								} catch (ParserConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								} catch (SAXException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return MyTask.EXECUTE_FAILED;
								}
							}
							mTask.publishProgress(10000);

							return result;
						}
						
					},
					new OnTaskListener() {

						@Override
						public void onPreExecute() {
							// TODO Auto-generated method stub
							mProgressDlg = ProgressDialog.show(MyRegisterActivity.this, null, "Registering...");
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
								MyPreferences.setBoolean(MyApplication.PREF_REGISTERED, true);
								MyPreferences.setString(MyApplication.PREF_ISDN, isdn);

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
								// TODO: Print exception message

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

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (mTask != null) {
			if (mTask.getStatus() != AsyncTask.Status.FINISHED) {
				mProgressDlg = ProgressDialog.show(MyRegisterActivity.this,
					null, "Registering...");
			}
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (mTask != null) {
			mProgressDlg.dismiss();
		}
		
		super.onPause();
	}

}
