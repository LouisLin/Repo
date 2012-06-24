/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyAlarm;
import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyPendingIntent;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
	    setContentView(R.layout.main);
	    
	    // Fix part:
		Button startAlarm = (Button)findViewById(R.id.button1);
	    startAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAlarm.setInexactRepeating(10000,
					MyPendingIntent.getBroadcast(MyAlarmReceiver.class));
				
			}
	    	
	    });
	    Button stopAlarm = (Button)findViewById(R.id.button2);
	    stopAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAlarm.cancel(MyPendingIntent.getBroadcast(MyAlarmReceiver.class));
				
			}
	    	
	    });
	    Button settings = (Button)findViewById(R.id.button3);
	    settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyIntent.startActivity(MyPreferenceActivity.class);
			}
	    	
	    });
	}

}
