/**
 * 
 */
package com.my.app.test15;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.my.app.test15.lib.MyAlarm;
import com.my.app.test15.lib.MyAlertDialog;
import com.my.app.test15.lib.MyApp;
import com.my.app.test15.lib.MyIntent;
import com.my.app.test15.lib.MyLayoutInflater;
import com.my.app.test15.lib.MyNotification;
import com.my.app.test15.lib.MyPendingIntent;
import com.my.app.test15.lib.MyPreferences;
import com.my.app.test15.lib.MyTask;
import com.my.app.test15.lib.MyToast;
import com.my.app.test15.lib.MyTask.OnTaskListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Louis
 *
 */
public class MyLauncherActivity extends Activity {

	private MyTask mTask;
	private ProgressDialog mProgressDlg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

//	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.main);

	    boolean registered = MyPreferences.getBoolean(MyApplication.PREF_REGISTERED, false);

		Button query = (Button)findViewById(R.id.button1);
		Button register = (Button)findViewById(R.id.button5);
		if (registered) {
		    query.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mTask = new MyQueryTask(MyLauncherActivity.this,
						new OnTaskListener() {

//							private ProgressDialog mProgressDlg;

							@Override
							public void onPreExecute() {
								// TODO Auto-generated method stub
								mProgressDlg = ProgressDialog.show(MyLauncherActivity.this, null, "Querying...");
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
		    register.setVisibility(View.GONE);
		} else {
			query.setVisibility(View.GONE);
			register.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MyIntent.startActivity(MyRegisterActivity.class);
				}
				
			});
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (mTask != null) {
			if (mTask.getStatus() != AsyncTask.Status.FINISHED) {
				mProgressDlg = ProgressDialog.show(MyLauncherActivity.this,
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        menu.add("Settings")
        	.setIntent(new Intent(this, MyPreferenceActivity.class))
        	;
        menu.add("Test")
        	.setIntent(new Intent(this, MyTestPreferenceActivity.class))
        	;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		MyToast.show("item:" + item.getTitle());
		return false;
	}

}
