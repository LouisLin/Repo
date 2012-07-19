/**
 * 
 */
package com.my.app.test1.lib;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.my.app.test1.MyApplication;
import com.my.app.test1.R;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Louis
 *
 */
public class MyApp {
//	final private static java.util.UUID mUUID = java.util.UUID.randomUUID();
	final private static java.util.UUID mUUID = java.util.UUID.fromString("996572ea-f63e-418b-899b-e53f07cd1518");
	final public static String UUID = mUUID.toString();
	final public static int ID = mUUID.hashCode();

	private static Context mContext = null;
	private static String mName = null;
	
	private static ActivityLifecycleCallbacks mActivityLifecycleCallbacks = null;
	private static ComponentCallbacks mComponentCallbacks = null;
//	private static int mActivities = 0;

	public static Context getContext() {
		return mContext;
	}

	public static void setContext(Context context) {
		mContext = context;
	}

	public static String getName() {
		if (mName == null) {
			mName = mContext.getString(R.string.app_name);
		}
		return mName;
	}
	
	public static ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
		if (mActivityLifecycleCallbacks == null) {
			mActivityLifecycleCallbacks = new MyActivityLifecycleCallbacks();
		}
		return mActivityLifecycleCallbacks;
	}

	public static ComponentCallbacks getComponentCallbacks() {
		if (mComponentCallbacks == null) {
			mComponentCallbacks = new MyComponentCallbacks();
		}
		return mComponentCallbacks;
	}

}
