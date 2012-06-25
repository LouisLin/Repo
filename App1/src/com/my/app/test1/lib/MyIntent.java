/**
 * 
 */
package com.my.app.test1.lib;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author Louis
 *
 */
public class MyIntent {
	
	public static void startService(Class<?> cls) {
		Intent intent = new Intent(MyApp.getContext(), cls);
		MyApp.getContext().startService(intent);
	}

	public static void startService(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(MyApp.getContext(), cls)
			.putExtras(bundle);
		MyApp.getContext().startService(intent);
	}

	public static void startActivity(Class<?> cls) {
		Intent intent = new Intent(MyApp.getContext(), cls)
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK/* | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY*/);
		MyApp.getContext().startActivity(intent);
	}

	public static void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(MyApp.getContext(), cls)
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK/* | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY*/)
			.putExtras(bundle);
		MyApp.getContext().startActivity(intent);
	}

	public static void startSingleActivity(Class<?> cls) {
		Intent intent = new Intent(MyApp.getContext(), cls)
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		MyApp.getContext().startActivity(intent);
	}

	public static void startSingleActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(MyApp.getContext(), cls)
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
			| Intent.FLAG_ACTIVITY_SINGLE_TOP)
			.putExtras(bundle);
		MyApp.getContext().startActivity(intent);
	}

}
