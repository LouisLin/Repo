/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyAlarm;
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
	    
		final PendingIntent intent = PendingIntent.getBroadcast(
			this, 0, new Intent(this, MyAlarmReceiver.class), 0);

		Button startAlarm = (Button)findViewById(R.id.button1);
	    startAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAlarm.setInexactRepeating(30000, intent);
				
			}
	    	
	    });
	    Button stopAlarm = (Button)findViewById(R.id.button2);
	    stopAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAlarm.cancel(intent);
				
			}
	    	
	    });
	    
	}

}
