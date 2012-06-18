/**
 * 
 */
package com.my.app.test5.lib;

import com.my.app.test5.R;

import android.app.AlertDialog;
import android.content.Context;

/**
 * @author Louis
 *
 */
public class MyAlertDialog {

	public static AlertDialog.Builder getDefaultAlertDialogBuilder(Context context) {
		return new AlertDialog.Builder(context)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("MyAlert")
			.setCancelable(true)
			;
	}
	public static void alert() {
		
	}
}
