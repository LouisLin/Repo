/**
 * 
 */
package com.my.app.test1.lib;

import android.app.AlertDialog;
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
			.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					MyToast.show("Alert onCancel()");
				}
			})
			;
	}

	public static void alert(AlertDialog alert) {
		alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alert.show();
	}
	
	public static void dismiss(AlertDialog alert) {
		alert.dismiss();
	}

}
