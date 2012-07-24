/**
 * 
 */
package com.my.app.test1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.my.app.test1.lib.MyAlarm;
import com.my.app.test1.lib.MyAlertDialog;
import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyConvert;
import com.my.app.test1.lib.MyIntent;
import com.my.app.test1.lib.MyDate;
import com.my.app.test1.lib.MyPendingIntent;
import com.my.app.test1.lib.MyPreferences;
import com.my.app.test1.lib.MyToast;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author Louis
 *
 */
public class MyApplication extends Application {
	public static final long INTERVAL_DAY = AlarmManager.INTERVAL_DAY;
	public static final long INTERVAL_HALF_DAY = AlarmManager.INTERVAL_HALF_DAY;
	public static final long INTERVAL_HOUR = AlarmManager.INTERVAL_HOUR;
	public static final long INTERVAL_HALF_HOUR = AlarmManager.INTERVAL_HALF_HOUR;
	public static final long INTERVAL_FIFTEEN_MINUTES = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	public static final long INTERVAL_TEN_MINUTES = 60000;//600000;
	public static final long INTERVAL_FIVE_MINUTES = 30000;//300000;
	public static final long INTERVAL_THREE_MINUTE = 18000;//180000;
	public static final long INTERVAL_MINUTE = 6000;//60000;
	public static final long INTERVAL_HALF_MINUTE = 3000;//30000;

	public static final long STARTUP_DELAY = 1000;//INTERVAL_HALF_MINUTE;
	public static final long DEF_ALARM_INTERVAL = 10000;//INTERVAL_HALF_DAY;
	
	public static final String PREF_REGISTERED = "registered";
	public static final String PREF_ISDN = "isdn";
	
	public static final String PREF_SOUND_EFFECT = "sound";
	public static final String PREF_VIBRATE_EFFECT = "vibrate";
	public static final String PREF_DAY_NOTIFY = "day_notify";
	public static final String PREF_DAY_PRECEDING_1 = "day_preceding_1";
	public static final String PREF_DAY_PRECEDING_2 = "day_preceding_2";
	public static final String PREF_NUM_NOTIFY = "num_notify";
	public static final String PREF_NUM_SUCCEEDING = "succeeding";
	public static final String PREF_NUM_MY_TURN = "my_turn";
	public static final String PREF_NUM_PRECEDING_1 = "num_preceding_1";
	public static final String PREF_NUM_PRECEDING_3 = "num_preceding_3";
	public static final String PREF_NUM_PRECEDING_5 = "num_preceding_5";
	public static final String PREF_NUM_PRECEDING_10 = "num_preceding_10";
	public static final String PREF_NUM_PRECEDING_20 = "num_preceding_20";
	
	public static final String TEST_PREF_SHOW_SDK_INFO = "show_info";
	public static final String TEST_PREF_REGISTERED = "registered";
	public static final String TEST_PREF_ALARM_ENABLE = "alarm_enable";
	public static final String TEST_PREF_LOCAL_REGISTER = "local_register";
	public static final String TEST_PREF_REGISTER_TIMEOUT = "register_timeout";
	public static final String TEST_PREF_REGISTER_URI = "register_uri";
	public static final String TEST_PREF_LOCAL_QUERY = "local_query";
	public static final String TEST_PREF_QUERY_TIMEOUT = "query_timeout";
	public static final String TEST_PREF_QUERY_URI = "query_uri";

	private static enum RegisterTags {
		ERROR
	};

	private static enum QueryTags {
		ERROR,
		REC_NUM,
		REG_REC
	};
	private static enum QueryRecTags {
		HOSPITAL,
		DEPARTMENT,
		REG_DATE,
		DIAG_DATE,
		REG_NO,
		DIAG_NO,
		EST_TIME
	};

	private long mAlarmInterval = DEF_ALARM_INTERVAL;
	private boolean mAlarmSet = false;
	private	boolean mHadNotified = false;
	private AlertDialog mGlobalAlert = null;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");

	private String[] mRegTags = null;
	private Bundle mRegisterStatus = new Bundle();
	private boolean mRegisterStatusUpdated = false;

	private String[] mTags = null;
	private String[] mRecTags = null;
	private Bundle mQueryStatus = new Bundle();
	private boolean mQueryStatusUpdated = false;

	private int mTEST_UpdateQueryCount = 0;

	public MyApplication() {
		super();
		// TODO Auto-generated constructor stub
		MyApp.setContext(this);
	}
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
//		MyToast.show("onConfigurationChanged()");

		super.onConfigurationChanged(newConfig);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		MyToast.show("onCreate()");
		if (false/*MyPreferences.getBoolean(MyApplication.TEST_PREF_SHOW_SDK_INFO, false)*/) {
			AlertDialog alert = MyAlertDialog.getNonActionsAlertDialogBuilder()
				.setMessage("Board=" + Build.BOARD
					+ "\nBootloader=" + Build.BOOTLOADER
					+ "\nBrand=" + Build.BRAND
					+ "\nDevice=" + Build.DEVICE
					+ "\nDisplay=" + Build.DISPLAY
					+ "\nFingerPrint=" + Build.FINGERPRINT
					+ "\nHardware=" + Build.HARDWARE
					+ "\nId=" + Build.ID
					+ "\nManufacturer=" + Build.MANUFACTURER
					+ "\nModel=" + Build.MODEL
					+ "\nProduct=" + Build.PRODUCT
					+ "\nRadio=" + Build.RADIO
					+ "\nSerial=" + Build.SERIAL
					+ "\nTags=" + Build.TAGS
					+ "\nTime=" + Build.TIME
					+ "\nType=" + Build.TYPE
					+ "\nUser=" + Build.USER
					+ "\nCodeName=" + Build.VERSION.CODENAME
					+ "\nIncremental=" + Build.VERSION.INCREMENTAL
					+ "\nRelease=" + Build.VERSION.RELEASE
					+ "\nSDK=" + Build.VERSION.SDK
					+ "\nSDK_INT=" + Build.VERSION.SDK_INT)
				.create();
			MyAlertDialog.alert(alert);
/*
			MyToast.showLong("Board=" + Build.BOARD
				+ "\nBootloader=" + Build.BOOTLOADER
				+ "\nBrand=" + Build.BRAND
				+ "\nDevice=" + Build.DEVICE
				+ "\nDisplay=" + Build.DISPLAY
				+ "\nFingerPrint=" + Build.FINGERPRINT
				+ "\nHardware=" + Build.HARDWARE
				+ "\nId=" + Build.ID
				+ "\nManufacturer=" + Build.MANUFACTURER
				+ "\nModel=" + Build.MODEL
				+ "\nProduct=" + Build.PRODUCT
				+ "\nRadio=" + Build.RADIO
				+ "\nSerial=" + Build.SERIAL
				+ "\nTags=" + Build.TAGS
				+ "\nTime=" + Build.TIME
				+ "\nType=" + Build.TYPE
				+ "\nUser=" + Build.USER
				+ "\nCodeName=" + Build.VERSION.CODENAME
				+ "\nIncremental=" + Build.VERSION.INCREMENTAL
				+ "\nRelease=" + Build.VERSION.RELEASE
				+ "\nSDK=" + Build.VERSION.SDK
				+ "\nSDK_INT=" + Build.VERSION.SDK_INT
				);
*/
		}

		registerActivityLifecycleCallbacks(MyApp.getActivityLifecycleCallbacks());
		registerComponentCallbacks(MyApp.getComponentCallbacks());

		mTags = getResources().getStringArray(R.array.query_status_tag);
		mRecTags = getResources().getStringArray(R.array.query_status_rec_tag);
		
		boolean registered = MyPreferences.getBoolean(MyApplication.PREF_REGISTERED, false);
		if (!registered) {
			mRegTags = getResources().getStringArray(R.array.register_status_tag);
		} else {
			// for TEST ONLY
			mRegTags = getResources().getStringArray(R.array.register_status_tag);
			
			startAlarm(MyApplication.STARTUP_DELAY);
		}

		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
//		MyToast.show("onLowMemory()");

		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
//		MyToast.show("onTerminate()");
		
		unregisterComponentCallbacks(MyApp.getComponentCallbacks());
		unregisterActivityLifecycleCallbacks(MyApp.getActivityLifecycleCallbacks());
		
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
//		MyToast.show("onTrimMemory()");

		super.onTrimMemory(level);
	}

	public long getAlarmInterval() {
		return mAlarmInterval;
	}


	public void setAlarmInterval(long interval) {
		mAlarmInterval = interval;
	}


	public void adjustAlarmInterval(int dateDiff, int numDiff) {
		long oldInterval = mAlarmInterval;

		if (dateDiff != 0) {
			mAlarmInterval = MyApplication.INTERVAL_HALF_DAY;
		} else {
			if (numDiff > 20) {
				mAlarmInterval = MyApplication.INTERVAL_TEN_MINUTES;
			} else if (numDiff > 10) {
				mAlarmInterval = MyApplication.INTERVAL_FIVE_MINUTES;
			} else if (numDiff > 5) {
				mAlarmInterval = MyApplication.INTERVAL_THREE_MINUTE;
			} else if (numDiff > 3) {				
				mAlarmInterval = MyApplication.INTERVAL_MINUTE;
			} else if (numDiff > 0) {
				mAlarmInterval = MyApplication.INTERVAL_HALF_MINUTE;
			} else {
				mAlarmInterval = MyApplication.INTERVAL_MINUTE;
			}
		}
		
		if (mAlarmInterval != oldInterval) {
			this.restartAlarm(this.getAlarmInterval());
		}
	}

	public boolean isAlarmSet() {
		return mAlarmSet;
	}

	public void setAlarmSet(boolean set) {
		mAlarmSet = set;
		MyPreferences.setBoolean(MyApplication.TEST_PREF_ALARM_ENABLE, mAlarmSet);
	}

	public void startAlarm(long delay) {
		// TODO Auto-generated method stub
		if (!this.isAlarmSet()) {
			this.setAlarmSet(true);
			MyAlarm.setInexactRepeating(delay,
				this.getAlarmInterval(),
				MyPendingIntent.getBroadcast(MyAlarmReceiver.class));
		}
	}
	
	public void stopAlarm() {
//		if (this.isAlarmSet()) {
			MyAlarm.cancel(
				MyPendingIntent.getBroadcast(MyAlarmReceiver.class));
			this.setAlarmSet(false);
//		}
	}
	
	public void restartAlarm(long delay) {
		if (this.isAlarmSet()) {
			MyAlarm.setInexactRepeating(delay,
				this.getAlarmInterval(),
				MyPendingIntent.getBroadcast(MyAlarmReceiver.class));			
		} else {
			this.startAlarm(delay);
		}
	}

	public boolean hadNotified() {
		return mHadNotified;
	}


	public void setHadNotified(boolean notified) {
		mHadNotified = notified;
	}


	public AlertDialog getGlobalAlert() {
		return mGlobalAlert;
	}


	public void setGlobalAlert(AlertDialog alert) {
		mGlobalAlert = alert;
	}


	public SimpleDateFormat getDateFormat() {
		return mDateFormat;
	}

	public URI getRegisterURI() {
		String registerURI = MyPreferences.getString(MyApplication.TEST_PREF_REGISTER_URI,
			getResources().getString(R.string.register_uri));
		URI uri = null;
		try {
			uri = new URI(registerURI);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uri;
	}

	public String getRegisterXml(String isdn, String name) {
		InputStream in = getResources().openRawResource(R.raw.register);
//		String sn = Build.SERIAL;
		String imei = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		String registerXml = String.format(
			MyConvert.inputStream2String(in),
			imei, imsi, isdn, name, "", "");
		
		return registerXml;
	}

	public void updateRegisterStatus(InputStream registerStatus) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();   
		Document doc = builder.parse(registerStatus);
		Element root = doc.getDocumentElement();
		/*
		<RegStatus>
			<Error>0</Error>
		</RegStatus>
		*/
		for (RegisterTags eTags : RegisterTags.values()) {
			NodeList nodes = root.getElementsByTagName(mRegTags[eTags.ordinal()]);
			if (nodes.getLength() > 0) {
				String value;
				switch (eTags) {
				case ERROR:
					value = nodes.item(0).getFirstChild().getNodeValue();
					mRegisterStatus.putInt(mRegTags[eTags.ordinal()], Integer.parseInt(value));
					break;
				}
			}
		}
		mRegisterStatusUpdated = true;
	}

	public boolean isRegisterStatusUpdated() {
		return mRegisterStatusUpdated;
	}

	public int getRegisterError() {
		return mRegisterStatus.getInt(mRegTags[RegisterTags.ERROR.ordinal()]);
	}
	
	public URI getQueryURI() {
		String queryURI = MyPreferences.getString(MyApplication.TEST_PREF_QUERY_URI,
				getResources().getString(R.string.query_uri));
		URI uri = null;
		try {
			uri = new URI(queryURI);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uri;
	}

	public String getQueryXml() {
		InputStream in = getResources().openRawResource(R.raw.query);
//		String sn = Build.SERIAL;
		String imei = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		String isdn = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		if (isdn == null || isdn.isEmpty()) {
			isdn = MyPreferences.getString(MyApplication.PREF_ISDN, "");
		}
		String queryXml = String.format(MyConvert.inputStream2String(in), imei, imsi, isdn);
		
		return queryXml;
	}

	public void updateQueryStatus(InputStream queryStatus) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();   
		Document doc = builder.parse(queryStatus);
		Element root = doc.getDocumentElement();
		/*
		<QueryStatus>
			<Error>0</Error>
			<RecNum>1</RecNum>
			<RegRec index="0">
				<Hospital>雙和醫院</Hospital>
				<Department>內科一診</Department>
				<RegDate>2012/06/01</RegDate>
				<DiagDate>2012/06/05</DiagDate>
				<RegNo>35</RegNo>
				<DiagNo>19</DiagNo>
				<EstTime>76</EstTime>
			</RegRec>
		</QueryStatus>
		*/
		for (QueryTags eTags : QueryTags.values()) {
			NodeList nodes = root.getElementsByTagName(mTags[eTags.ordinal()]);
			if (nodes.getLength() > 0) {
				String value;
				switch (eTags) {
				case ERROR:
					value = nodes.item(0).getFirstChild().getNodeValue();
					mQueryStatus.putInt(mTags[eTags.ordinal()], Integer.parseInt(value));
					break;
				case REC_NUM:
					value = nodes.item(0).getFirstChild().getNodeValue();
					mQueryStatus.putInt(mTags[eTags.ordinal()], Integer.parseInt(value));
					break;
				case REG_REC:
					for (int rec = 0; rec < nodes.getLength(); ++rec) {
						value = nodes.item(rec).getAttributes().getNamedItem("index").getNodeValue();
						mQueryStatus.putBundle(mTags[eTags.ordinal()] + value, new Bundle());
					}
					break;
				}
			}
		}
		for (int rec = 0; rec < getQueryRecords(); ++rec) {
			Bundle recBundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + rec);
			for (QueryRecTags eRecTags : QueryRecTags.values()) {
				NodeList nodes = root.getElementsByTagName(mRecTags[eRecTags.ordinal()]);
				if (nodes.getLength() > 0) {
					String value = nodes.item(rec).getFirstChild().getNodeValue();
					switch (eRecTags) {
					case HOSPITAL:
						recBundle.putString(mRecTags[eRecTags.ordinal()], value);
						break;
					case DEPARTMENT:
						recBundle.putString(mRecTags[eRecTags.ordinal()], value);
						break;
					case REG_DATE:
						recBundle.putString(mRecTags[eRecTags.ordinal()], value);
						break;
					case DIAG_DATE:
						recBundle.putString(mRecTags[eRecTags.ordinal()], value);
						break;
					case REG_NO:
						try {
							recBundle.putInt(mRecTags[eRecTags.ordinal()], Integer.parseInt(value));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();							
						}
						break;
					case DIAG_NO:
						try {
							recBundle.putInt(mRecTags[eRecTags.ordinal()], Integer.parseInt(value) + mTEST_UpdateQueryCount);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();							
						}
						break;
					case EST_TIME:
						try {
							recBundle.putInt(mRecTags[eRecTags.ordinal()], Integer.parseInt(value));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();							
						}
						break;
					}
				}			
			}
		}
		++mTEST_UpdateQueryCount;
		mQueryStatusUpdated = true;
	}
	
	public boolean isQueryStatusUpdated() {
		return mQueryStatusUpdated;
	}

	public int getQueryError() {
		return mQueryStatus.getInt(mTags[QueryTags.ERROR.ordinal()]);
	}
	
	public int getQueryRecords() {
		return mQueryStatus.getInt(mTags[QueryTags.REC_NUM.ordinal()]);
	}

	public String getQueryHospital(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getString(mRecTags[QueryRecTags.HOSPITAL.ordinal()]);
	}

	public String getQueryDepartment(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getString(mRecTags[QueryRecTags.DEPARTMENT.ordinal()]);
	}

	public Date getQueryRegDate(int index) throws IndexOutOfBoundsException, NullPointerException, ParseException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		String value = bundle.getString(mRecTags[QueryRecTags.REG_DATE.ordinal()]);
		if (value == null) {
			throw new NullPointerException();
		}
//		return mDateFormat.parse(value);
		return MyDate.yesterday();
	}

	public Date getQueryDiagDate(int index) throws IndexOutOfBoundsException, NullPointerException, ParseException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		String value = bundle.getString(mRecTags[QueryRecTags.DIAG_DATE.ordinal()]);
		if (value == null) {
			throw new NullPointerException();
		}
//		return mDateFormat.parse(value);
		return MyDate.today();
	}

	public int getQueryRegNo(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getInt(mRecTags[QueryRecTags.REG_NO.ordinal()]);
	}

	public int getQueryDiagNo(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getInt(mRecTags[QueryRecTags.DIAG_NO.ordinal()]);
	}

	public int getQueryEstimateTime(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getInt(mRecTags[QueryRecTags.DIAG_NO.ordinal()]);
	}

}
