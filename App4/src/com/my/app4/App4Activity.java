package com.my.app4;

import com.my.app4.lib.MyIntent;
import com.my.app4.lib.MyToast;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

public class App4Activity extends Activity implements OnClickListener {
	private Button mButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mButton = (Button)findViewById(R.id.button1);
		mButton.setOnClickListener(this);
		MyToast.show(this, "OnCreate");
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean numNotify = preferences.getBoolean("num_notify", false);
		Log.d("App4", "numNotify=" + numNotify);
		((Switch)findViewById(R.id.switch1)).setChecked(numNotify);
		boolean succeed = preferences.getBoolean("succeeding", false);
		Log.d("App4", "succeed=" + succeed);
		((CheckBox)findViewById(R.id.checkBox1)).setChecked(succeed);
		boolean preceding1 = preferences.getBoolean("num_preceding_1", false);
		Log.d("App4", "preceding1=" + preceding1);
		((CheckBox)findViewById(R.id.checkBox3)).setChecked(preceding1);
		boolean preceding3 = preferences.getBoolean("num_preceding_3", false);
		Log.d("App4", "preceding3=" + preceding3);
		((CheckBox)findViewById(R.id.checkBox4)).setChecked(preceding3);
		boolean preceding5 = preferences.getBoolean("num_preceding_5", false);
		Log.d("App4", "preceding5=" + preceding5);
		((CheckBox)findViewById(R.id.checkBox5)).setChecked(preceding5);
		boolean preceding10 = preferences.getBoolean("num_preceding_10", false);
		Log.d("App4", "preceding10=" + preceding10);
		((CheckBox)findViewById(R.id.checkBox6)).setChecked(preceding10);
		boolean preceding20 = preferences.getBoolean("num_preceding_20", false);
		Log.d("App4", "preceding20=" + preceding20);
		((CheckBox)findViewById(R.id.checkBox7)).setChecked(preceding20);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			MyIntent.startActivity(this, MyPrefActivity.class);
		}
	}

}