/**
 * 
 */
package com.my.app.test1.lib;

import android.content.Intent;

/**
 * @author Louis
 *
 */
public class MyIntent {
	
	public static void startService(Class<?> cls) {
		Intent intent = new Intent(MyApp.getContext(), cls);
		MyApp.getContext().startService(intent);
	}

	public static void startActivity(Class<?> cls) {
		Intent intent = new Intent(MyApp.getContext(), cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK/* | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY*/);
		MyApp.getContext().startActivity(intent);
	}

	public static void startSingleActivity(Class<?> cls) {
		Intent intent = new Intent(MyApp.getContext(), cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		MyApp.getContext().startActivity(intent);
	}

}
