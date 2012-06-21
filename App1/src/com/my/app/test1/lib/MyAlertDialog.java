/**
 * 
 */
package com.my.app.test1.lib;

import com.my.app.test1.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

/**
 * @author Louis
 *
 */
public class MyAlertDialog {

	public static AlertDialog.Builder getDefaultAlertDialogBuilder() {
		return new AlertDialog.Builder(MyApp.getContext())
//			.setIcon(R.drawable.ic_launcher)
//			.setIconAttribute(android.R.attr.alertDialogIcon)
			.setTitle(MyApp.getName())
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

	public static void alert(AlertDialog alert) {
		alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alert.show();
	}
}
