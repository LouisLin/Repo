/**
 * 
 */
package com.my.app.test1.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Louis
 *
 */
public class MyConvert {
	public static String inputStream2String(InputStream in) {
		StringWriter out = new StringWriter();
		if (in != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int count;
			char[] buffer = new char[1024];
		    try {
				while ((count = reader.read(buffer)) >= 0) {
					out.write(buffer, 0, count);
				}
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return out.toString();
	}
}
