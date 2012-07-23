/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyPreferences;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

/**
 * @author Louis
 *
 */
public class MyPreferenceActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    /*
	    addPreferencesFromResource(R.xml.settings_notify);
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
		queryTimeout.setTitle("Register Timeout");
		root.addPreference(queryTimeout);

    	uri = MyPreferences.getString(
    	   	MyApplication.TEST_PREF_QUERY_URI,
    	   	getResources().getString(R.string.query_uri));

    	EditTextPreference queryUri = new EditTextPreference(this);
		queryUri.setKey(MyApplication.TEST_PREF_QUERY_URI);
		queryUri.setSummary(uri);
		queryUri.setText(uri);

        setPreferenceScreen(root);
	}

}
