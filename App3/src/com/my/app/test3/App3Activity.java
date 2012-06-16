package com.my.app.test3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.my.app.test3.lib.MyConvert;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class App3Activity extends Activity {
	Thread mWeb;

    public static final class Tag {
        public static final int TAG_ERROR = -3;
        public static final int TAG_RECNUM = -2;
        public static final int TAG_REGREC = -1;
        public static final int TAG_HOSPITAL = 0;
        public static final int TAG_DEPART = 1;
        public static final int TAG_REGDATE = 2;
        public static final int TAG_DIAGDATE = 3;
        public static final int TAG_REGNO = 4;
        public static final int TAG_DIAGNO = 5;
        public static final int TAG_ESTTIME = 6;
        public static final int TAG_COUNT = 7;
        public static final String name[] = {
        	"Hospital", "Department", "RegDate", "DiagDate", "RegNo", "DiagNo", "EstTime"
        };
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView view[] = new TextView[Tag.TAG_COUNT];
        view[0] = (TextView)findViewById(R.id.textView1);
        view[1] = (TextView)findViewById(R.id.textView2);
        view[2] = (TextView)findViewById(R.id.textView3);
        view[3] = (TextView)findViewById(R.id.textView4);
        view[4] = (TextView)findViewById(R.id.textView5);
        view[5] = (TextView)findViewById(R.id.textView6);
        view[6] = (TextView)findViewById(R.id.textView7);
		final String data[] = new String[Tag.TAG_COUNT];
		InputStream in = getResources().openRawResource(R.raw.query);
		final String query = MyConvert.inputStream2String(in);

        mWeb = new Thread(new Runnable() {
			public void run() {
				try {
					URI uri = new URI("http://192.168.1.2/Android/registration.xml");
					String sn = Build.SERIAL;
					String imsi = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
					String isdn = "0939064759";
					String queryXml = String.format(query, sn, imsi, isdn);

					HttpPost req = new HttpPost(uri);
					StringEntity entify = new StringEntity(queryXml, HTTP.UTF_8);
					entify.setContentType("text/xml");
			        req.setEntity(entify);

					HttpResponse resp = new DefaultHttpClient().execute(req);
					int status = resp.getStatusLine().getStatusCode();
					if (status == 200) {
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();   
						Document doc = builder.parse(resp.getEntity().getContent());
						Element root = doc.getDocumentElement();
						for (int i = 0; i < Tag.TAG_COUNT; ++i) {
							NodeList nodes = root.getElementsByTagName(Tag.name[i]);
							data[i] = nodes.item(0).getFirstChild().getNodeValue();
						}
					}
					resp.getEntity().consumeContent();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					Log.e("App3", "ClientProtocolException");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("App3", "IOException");
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					Log.e("App3", "IllegalStateException");
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					Log.e("App3", "SAXException");
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					Log.e("App3", "ParserConfigurationException");
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					view[0].post(new Runnable() {
						public void run() {
							for (int i = 0; i < Tag.TAG_COUNT; ++i) {
								view[i].setText(data[i]);
							}
						}
					});
				}
			}
		});
		try {
			mWeb.start();
		} catch (IllegalThreadStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}