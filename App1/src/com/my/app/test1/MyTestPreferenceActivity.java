package com.my.app.test1;

import com.my.app.test1.lib.MyPreferences;
import com.my.app.test1.lib.MyToast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

public class MyTestPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	final public static int TEST_PREF_INDEX_REGISTER_URI = 5;
	final public static int TEST_PREF_INDEX_QUERY_URI = 8;

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
    	PreferenceManager.getDefaultSharedPreferences(this)
    		.registerOnSharedPreferenceChangeListener(this);
/*
	    addPreferencesFromResource(R.xml.settings_test);

    	String uri = MyPreferences.getString(
    	   	MyApplication.TEST_PREF_REGISTER_URI,
    	   	getResources().getString(R.string.register_uri));
        EditTextPreference etPref = (EditTextPreference)getPreferenceScreen()
        	.getPreference(MyTestPreferenceActivity.TEST_PREF_INDEX_REGISTER_URI);
    	    etPref.setSummary(uri);
    	    etPref.setText(uri);

    	uri = MyPreferences.getString(
	    	MyApplication.TEST_PREF_QUERY_URI,
	    	getResources().getString(R.string.query_uri));
    	etPref = (EditTextPreference)getPreferenceScreen()
    		.getPreference(MyTestPreferenceActivity.TEST_PREF_INDEX_QUERY_URI);
	    etPref.setSummary(uri);
	    etPref.setText(uri);
*/
        // Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

		CheckBoxPreference showSDK = new CheckBoxPreference(this);
		showSDK.setKey(MyApplication.TEST_PREF_SHOW_SDK_INFO);
		showSDK.setTitle("Show SDK Info at startup");
		root.addPreference(showSDK);

        SwitchPreference alarm = new SwitchPreference(this);
        alarm.setKey(MyApplication.TEST_PREF_ALARM_ENABLE);
        alarm.setTitle("Alarm Enable");
        root.addPreference(alarm);

		CheckBoxPreference register = new CheckBoxPreference(this);
		register.setKey(MyApplication.TEST_PREF_REGISTERED);
		register.setTitle("Registered");
		root.addPreference(register);

        SwitchPreference localReg = new SwitchPreference(this);
        localReg.setKey(MyApplication.TEST_PREF_LOCAL_REGISTER);
        localReg.setTitle("Local Register");
        root.addPreference(localReg);

		CheckBoxPreference regTimeout = new CheckBoxPreference(this);
		regTimeout.setKey(MyApplication.TEST_PREF_REGISTER_TIMEOUT);
		regTimeout.setTitle("Register Timeout");
		root.addPreference(regTimeout);

    	String uri = MyPreferences.getString(
    	   	MyApplication.TEST_PREF_REGISTER_URI,
    	   	getResources().getString(R.string.register_uri));

    	EditTextPreference regUri = new EditTextPreference(this);
		regUri.setKey(MyApplication.TEST_PREF_REGISTER_URI);
		regUri.setSummary(uri);
		regUri.setText(uri);

        SwitchPreference localQuery = new SwitchPreference(this);
        localQuery.setKey(MyApplication.TEST_PREF_LOCAL_QUERY);
        localQuery.setTitle("Local Query");
        root.addPreference(localQuery);

		CheckBoxPreference queryTimeout = new CheckBoxPreference(this);
		queryTimeout.setKey(MyApplication.TEST_PREF_QUERY_TIMEOUT);
		queryTimeout.setTitle("Query Timeout");
		root.addPreference(queryTimeout);

    	uri = MyPreferences.getString(
    	   	MyApplication.TEST_PREF_QUERY_URI,
    	   	getResources().getString(R.string.query_uri));

    	EditTextPreference queryUri = new EditTextPreference(this);
		queryUri.setKey(MyApplication.TEST_PREF_QUERY_URI);
		queryUri.setSummary(uri);
		queryUri.setText(uri);

        setPreferenceScreen(root);

        localReg.setDisableDependentsState(false);
        regTimeout.setDependency(MyApplication.TEST_PREF_LOCAL_REGISTER);
        localQuery.setDisableDependentsState(false);
		queryTimeout.setDependency(MyApplication.TEST_PREF_LOCAL_QUERY);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		PreferenceManager.getDefaultSharedPreferences(this)
			.unregisterOnSharedPreferenceChangeListener(this);
		
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
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
		} else if (key.compareTo(MyApplication.TEST_PREF_REGISTER_URI) == 0) {
	    	String uri = MyPreferences.getString(
	        	MyApplication.TEST_PREF_REGISTER_URI,
	        	getResources().getString(R.string.register_uri));
            ((EditTextPreference)getPreferenceScreen().getPreference(4))
	        	.setSummary(uri);
		} else if (key.compareTo(MyApplication.TEST_PREF_QUERY_URI) == 0) {
	    	String uri = MyPreferences.getString(
		        	MyApplication.TEST_PREF_QUERY_URI,
		        	getResources().getString(R.string.query_uri));
            ((EditTextPreference)getPreferenceScreen().getPreference(7))
	        	.setSummary(uri);
		}

	}

}
