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
public class MyLauncherActivity extends Activity implements OnClickListener {

	private PendingIntent intent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.main);
	    
	    Button startAlarm = (Button)findViewById(R.id.button1);
	    startAlarm.setOnClickListener(this);
	    Button stopAlarm = (Button)findViewById(R.id.button2);
	    stopAlarm.setOnClickListener(this);
	    
		intent = PendingIntent.getBroadcast(this, 0,
			new Intent(this, MyAlarmReceiver.class), 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			MyAlarm.setInexactRepeating(30000, intent);
		} else {
			MyAlarm.cancel(intent);
		}
	}
	
}
