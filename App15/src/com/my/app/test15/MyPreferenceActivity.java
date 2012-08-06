/**
 * 
 */
package com.my.app.test15;

import com.my.app.test15.lib.MyPreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
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

		CheckBoxPreference sound = new CheckBoxPreference(this);
		sound.setKey(MyApplication.PREF_SOUND_EFFECT);
		sound.setTitle("Sound Effect");
		root.addPreference(sound);

		CheckBoxPreference vibrate = new CheckBoxPreference(this);
		vibrate.setKey(MyApplication.PREF_VIBRATE_EFFECT);
		vibrate.setTitle("Vibrate Effect");
		root.addPreference(vibrate);

		PreferenceCategory day = new PreferenceCategory(this);
		day.setTitle("Day Notification");
		root.addPreference(day);

		SwitchPreference dayNotify = new SwitchPreference(this);
		dayNotify.setKey(MyApplication.PREF_DAY_NOTIFY);
		dayNotify.setTitle("Notify me");
		day.addPreference(dayNotify);

		CheckBoxPreference day1 = new CheckBoxPreference(this);
		day1.setKey(MyApplication.PREF_DAY_PRECEDING_1);
		day1.setTitle("Preceding days: 1");
		day.addPreference(day1);

		CheckBoxPreference day2 = new CheckBoxPreference(this);
		day2.setKey(MyApplication.PREF_DAY_PRECEDING_2);
		day2.setTitle("Preceding days: 2");
		day.addPreference(day2);

		PreferenceCategory num = new PreferenceCategory(this);
		num.setTitle("Number Notification");
		root.addPreference(num);

		SwitchPreference numNotify = new SwitchPreference(this);
		numNotify.setKey(MyApplication.PREF_NUM_NOTIFY);
		numNotify.setTitle("Notify me");
		num.addPreference(numNotify);

		CheckBoxPreference numS = new CheckBoxPreference(this);
		numS.setKey(MyApplication.PREF_NUM_SUCCEEDING);
		numS.setTitle("Succeeding my number");
		num.addPreference(numS);

		CheckBoxPreference numMyTurn = new CheckBoxPreference(this);
		numMyTurn.setKey(MyApplication.PREF_NUM_MY_TURN);
		numMyTurn.setTitle("Succeeding my number");
		num.addPreference(numMyTurn);

		CheckBoxPreference num1 = new CheckBoxPreference(this);
		num1.setKey(MyApplication.PREF_NUM_PRECEDING_1);
		num1.setTitle("Preceding numbers: 1");
		num.addPreference(num1);

		CheckBoxPreference num3 = new CheckBoxPreference(this);
		num3.setKey(MyApplication.PREF_NUM_PRECEDING_3);
		num3.setTitle("Preceding numbers: 3");
		num.addPreference(num3);

		CheckBoxPreference num5 = new CheckBoxPreference(this);
		num5.setKey(MyApplication.PREF_NUM_PRECEDING_5);
		num5.setTitle("Preceding numbers: 5");
		num.addPreference(num5);

		CheckBoxPreference num10 = new CheckBoxPreference(this);
		num10.setKey(MyApplication.PREF_NUM_PRECEDING_10);
		num10.setTitle("Preceding numbers: 10");
		num.addPreference(num10);

		CheckBoxPreference num20 = new CheckBoxPreference(this);
		num20.setKey(MyApplication.PREF_NUM_PRECEDING_20);
		num20.setTitle("Preceding numbers: 20");
		num.addPreference(num20);

		setPreferenceScreen(root);
		
		dayNotify.setDisableDependentsState(false);
		day1.setDependency(MyApplication.PREF_DAY_NOTIFY);
		day2.setDependency(MyApplication.PREF_DAY_NOTIFY);
		numNotify.setDisableDependentsState(false);
		numS.setDependency(MyApplication.PREF_NUM_NOTIFY);
		numMyTurn.setDependency(MyApplication.PREF_NUM_NOTIFY);
		num1.setDependency(MyApplication.PREF_NUM_NOTIFY);
		num3.setDependency(MyApplication.PREF_NUM_NOTIFY);
		num5.setDependency(MyApplication.PREF_NUM_NOTIFY);
		num10.setDependency(MyApplication.PREF_NUM_NOTIFY);
		num20.setDependency(MyApplication.PREF_NUM_NOTIFY);

	}

}
