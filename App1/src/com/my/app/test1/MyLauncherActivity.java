/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyAlarm;
import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyPendingIntent;
import com.my.app.test1.lib.MyPreferences;
import com.my.app.test1.lib.MyToast;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Louis
 *
 */
public class MyLauncherActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
//	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.main);

		Button query = (Button)findViewById(R.id.button3);
	    query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putBoolean("polling", true);
				MyIntent.startService(MyBackgroundService.class, bundle);
			}
	    	
	    });
		Button startAlarm = (Button)findViewById(R.id.button1);
	    startAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MyApplication)MyLauncherActivity.this.getApplicationContext())
					.startAlarm(0);

			}
	    	
	    });
	    Button stopAlarm = (Button)findViewById(R.id.button2);
	    stopAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MyApplication)MyLauncherActivity.this.getApplicationContext())
					.stopAlarm();
				
			}
	    	
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        menu.add("Settings")
        	.setIntent(new Intent(this, MyPreferenceActivity.class))
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
