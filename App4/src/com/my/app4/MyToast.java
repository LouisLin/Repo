/**
 * 
 */
package com.my.app4;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Louis
 *
 */
public class MyToast {

	public static void show(Context context, CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showLong(Context context, CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}
