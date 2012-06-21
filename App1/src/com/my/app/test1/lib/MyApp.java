/**
 * 
 */
package com.my.app.test1.lib;

import com.my.app.test1.R;

import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyApp {
	final private static java.util.UUID mUUID = java.util.UUID.randomUUID();
	final public static String UUID = mUUID.toString();
	final public static int ID = mUUID.hashCode();

	private static Context mContext = null;
	private static String mName = null;

	public static Context getContext() {
		return mContext;
	}

	public static void setContext(Context context) {
		MyApp.mContext = context;
	}

	public static String getName() {
		if (mName == null) {
			mName = mContext.getString(R.string.app_name);
		}
		return mName;
	}

}
