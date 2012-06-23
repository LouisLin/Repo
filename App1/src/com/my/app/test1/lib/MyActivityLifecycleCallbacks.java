/**
 * 
 */
package com.my.app.test1.lib;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

/**
 * @author Louis
 *
 */
public class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

	@Override
	public void onActivityCreated(Activity activity,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivityCreated():" + activity.getClass().getSimpleName());
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivityDestroyed():" + activity.getClass().getSimpleName());

//		if (--MyApp.mActivities == 0) {
//			getApplicationContext().unregisterComponentCallbacks();
//			mContext.getApplicationContext().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
//		}
	}

	@Override
	public void onActivityPaused(Activity activity) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivityPaused():" + activity.getClass().getSimpleName());			
	}

	@Override
	public void onActivityResumed(Activity activity) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivityResumed():" + activity.getClass().getSimpleName());			
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity,
			Bundle outState) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivitySaveInstanceState():" + activity.getClass().getSimpleName());			
	}

	@Override
	public void onActivityStarted(Activity activity) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivityStarted():" + activity.getClass().getSimpleName());			
	}

	@Override
	public void onActivityStopped(Activity activity) {
		// TODO Auto-generated method stub
//		MyToast.show("onActivityStopped():" + activity.getClass().getSimpleName());			
	}
}
