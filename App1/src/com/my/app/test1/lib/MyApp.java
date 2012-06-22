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

import com.my.app.test1.R;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Louis
 *
 */
public class MyApp {
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

	final private static java.util.UUID mUUID = java.util.UUID.randomUUID();
	final public static String UUID = mUUID.toString();
	final public static int ID = mUUID.hashCode();

	private static Context mContext = null;
	private static String mName = null;

	private static String[] mTags = null;
	private static String[] mRecTags = null;
	private static Bundle mQueryStatus = new Bundle();

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

	public static void updateQueryStatus(InputStream queryStatus) throws ParserConfigurationException, SAXException, IOException {
		if (mTags == null) {
			mTags = mContext.getResources().getStringArray(R.array.query_status_tag);
		}
		if (mRecTags == null) {
			mRecTags = mContext.getResources().getStringArray(R.array.query_status_rec_tag);
		}
		
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
				<EstTime>76分鐘</EstTime>
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
						try {
							date = DateFormat.getDateInstance(DateFormat.SHORT).parse(value);
							value = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						recBundle.putString(mRecTags[eRecTags.ordinal()], value);
						break;
					case DIAG_DATE:
						try {
							date = DateFormat.getDateInstance(DateFormat.SHORT).parse(value);
							value = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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

	public static int getQueryError() {
		return mQueryStatus.getInt(mTags[QueryTags.ERROR.ordinal()]);
	}
	
	public static int getQueryRecords() {
		return mQueryStatus.getInt(mTags[QueryTags.REC_NUM.ordinal()]);
	}

	public static String getQueryHospital(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getString(mRecTags[QueryRecTags.HOSPITAL.ordinal()]);
	}
	
	public static Date getQueryRegDate(int index) throws IndexOutOfBoundsException, NullPointerException, ParseException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		String value = bundle.getString(mRecTags[QueryRecTags.REG_DATE.ordinal()]);
		if (value == null) {
			throw new NullPointerException();
		}
		return DateFormat.getDateInstance(DateFormat.SHORT).parse(value);
	}

	public static Date getQueryDiagDate(int index) throws IndexOutOfBoundsException, NullPointerException, ParseException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		String value = bundle.getString(mRecTags[QueryRecTags.DIAG_DATE.ordinal()]);
		if (value == null) {
			throw new NullPointerException();
		}
		return DateFormat.getDateInstance(DateFormat.SHORT).parse(value);
	}

	public static int getQueryRegNo(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getInt(mRecTags[QueryRecTags.REG_NO.ordinal()]);
	}

	public static int getQueryDiagNo(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getInt(mRecTags[QueryRecTags.DIAG_NO.ordinal()]);
	}

	public static int getQueryEstimateTime(int index) throws IndexOutOfBoundsException {
		Bundle bundle = mQueryStatus.getBundle(mTags[QueryTags.REG_REC.ordinal()] + index);
		if (bundle == null) {
			throw new IndexOutOfBoundsException();
		}
		return bundle.getInt(mRecTags[QueryRecTags.DIAG_NO.ordinal()]);
	}

}
