/**
 * 
 */
package com.my.app.test1;

import android.os.Bundle;
import android.preference.PreferenceActivity;

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
	    addPreferencesFromResource(R.xml.settings_notify);
	    getActionBar().setTitle("Settings");
	}

}
