/**
 * 
 */
package com.my.app4;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author Louis
 *
 */
public class MyPrefActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    addPreferencesFromResource(R.xml.notify);
	}

}
