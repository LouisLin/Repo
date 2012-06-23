/**
 * 
 */
package com.my.app.test1;

import java.io.IOException;
import java.io.InputStream;
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

import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyToast;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Louis
 *
 */
public class MyApplication extends Application {
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

	private String[] mTags = null;
	private String[] mRecTags = null;
	private Bundle mQueryStatus = new Bundle();
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public MyApplication() {
		super();
		// TODO Auto-generated constructor stub
		MyApp.setContext(this);
	}
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		MyToast.show("onConfigurationChanged()");

		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		MyToast.show("onCreate()");

		mTags = getResources().getStringArray(R.array.query_status_tag);
		mRecTags = getResources().getStringArray(R.array.query_status_rec_tag);
		
		registerActivityLifecycleCallbacks(MyApp.getActivityLifecycleCallbacks());
		registerComponentCallbacks(MyApp.getComponentCallbacks());

		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		MyToast.show("onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		MyToast.show("onTerminate()");
		
		unregisterComponentCallbacks(MyApp.getComponentCallbacks());
		unregisterActivityLifecycleCallbacks(MyApp.getActivityLifecycleCallbacks());
		
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		MyToast.show("onTrimMemory()");

		super.onTrimMemory(level);
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
						Log.d("App1", "RegRec index=" + value);
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
					Date date;
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
							recBundle.putInt(mRecTags[eRecTags.ordinal()], Integer.parseInt(value));
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
		return mDateFormat.parse(value);
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
		return mDateFormat.parse(value);
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
