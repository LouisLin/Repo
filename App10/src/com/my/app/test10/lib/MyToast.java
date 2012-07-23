/**
 * 
 */
package com.my.app.test10.lib;

import android.widget.Toast;

/**
 * @author Louis
 *
 */
public class MyToast {

	public static void show(CharSequence text) {
		Toast.makeText(MyApp.getContext(), text, Toast.LENGTH_SHORT).show();
	}

	public static void showLong(CharSequence text) {
		Toast.makeText(MyApp.getContext(), text, Toast.LENGTH_LONG).show();
	}
}
