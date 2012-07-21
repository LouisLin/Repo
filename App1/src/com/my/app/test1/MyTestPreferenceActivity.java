package com.my.app.test1;

import com.my.app.test1.lib.MyToast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class MyTestPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    addPreferencesFromResource(R.xml.settings_test);
	    PreferenceManager.getDefaultSharedPreferences(this)
	    	.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		PreferenceManager.getDefaultSharedPreferences(this)
			.unregisterOnSharedPreferenceChangeListener(this);
		
		super.onDestroy();
	}

	@Override
	public void onSharedPreferenceChanged(
		SharedPreferences sharedPreferences, String key) {
		// TODO Auto-generated method stub
		MyApplication app = ((MyApplication)getApplicationContext());

		MyToast.show("key:" + key + " changed");
		if (key.compareTo(MyApplication.TEST_PREF_ALARM_ENABLE) == 0) {
			if (sharedPreferences.getBoolean(key, false)) {
				if (!app.isAlarmSet()) {
					app.startAlarm(0);
					MyToast.show("Alarm start!");
				}
			} else {
				if (app.isAlarmSet()) {
					app.stopAlarm();
					MyToast.show("Alarm stopped!");
				}
			}
		}

	}

}
