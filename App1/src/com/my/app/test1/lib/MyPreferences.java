package com.my.app.test1.lib;

import java.util.Map;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class MyPreferences {

	public static boolean contains(String key) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.contains(key);
	}

	public static Map<String, ?> getString(String key) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getAll();
	}

	public static boolean getBoolean(String key, boolean defValue) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getBoolean(key, defValue);
	}
	
	public static void setBoolean(String key, boolean value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static float getFloat(String key, float defValue) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getFloat(key, defValue);
	}
	
	public static void setFloat(String key, float value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		Editor editor = preferences.edit();
		editor.putFloat(key, value);
		editor.apply();
	}

	public static int getInt(String key, int defValue) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getInt(key, defValue);
	}

	public static void setInt(String key, int value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public static long getLong(String key, long defValue) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getLong(key, defValue);
	}
	
	public static void setLong(String key, long value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public static String getString(String key, String defValue) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getString(key, defValue);
	}

	public static void setString(String key, String value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static Set<String> getStringSet(String key, Set<String> defValue) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		return preferences.getStringSet(key, defValue);
	}

	public static void setStringSet(String key, Set<String> value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		Editor editor = preferences.edit();
		editor.putStringSet(key, value);
		editor.apply();
	}

	public static void registerOnSharedPreferenceChangeListener(
		OnSharedPreferenceChangeListener listener) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		preferences.registerOnSharedPreferenceChangeListener(listener);
	}

	public static void unregisterOnSharedPreferenceChangeListener(
		OnSharedPreferenceChangeListener listener) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
		preferences.unregisterOnSharedPreferenceChangeListener(listener);
	}

	public static SharedPreferences get() {
		return PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());		
	}
}
