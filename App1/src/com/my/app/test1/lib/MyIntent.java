/**
 * 
 */
package com.my.app.test1.lib;

import android.content.Context;
import android.content.Intent;

/**
 * @author Louis
 *
 */
public class MyIntent {
	
	public static void startService(Context context, Class<?> cls) {
		Intent svcIntent = new Intent(context, cls);
		context.startService(svcIntent);
	}

	public static void startActivity(Context context, Class<?> cls) {
		Intent actIntent = new Intent(context, cls);
//		actIntent.setAction(Intent.ACTION_MAIN);
//		actIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK/* | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY*/);
		context.startActivity(actIntent);
	}

}
