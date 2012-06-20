/**
 * 
 */
package com.my.app.test6.lib;

import com.my.app.test6.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author Louis
 *
 */
public class MyAlertDialog {

	public static AlertDialog.Builder getDefaultAlertDialogBuilder(Context context) {
		String appName = context.getString(R.string.app_name);
		return new AlertDialog.Builder(context)
//			.setIcon(R.drawable.ic_launcher)
//			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setTitle(appName)
			.setMessage("Message...")
//			.setView(null)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNeutralButton("More...", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setCancelable(true)
			;
	}
	public static void alert(Context context, AlertDialog alert) {
		alert.show();
	}
}
