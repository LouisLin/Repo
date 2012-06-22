/**
 * 
 */
package com.my.app.test1;

import com.my.app.test1.lib.MyApp;
import com.my.app.test1.lib.MyToast;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * @author Louis
 *
 */
public class MyApplication extends Application {
	private ActivityLifecycleCallbacks mActivityLifecycleCallbacks =
		new ActivityLifecycleCallbacksImpl();
	private ComponentCallbacks mComponentCallbacks =
		new ComponentCallbacksImpl();
	public int mActivities = 0;

	public MyApplication() {
		super();
		// TODO Auto-generated constructor stub
		MyApp.setContext(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
//		MyToast.show("onConfigurationChanged()");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		MyToast.show("onCreate()");
		registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
		registerComponentCallbacks(mComponentCallbacks );
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
//		MyToast.show("onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
//		MyToast.show("onTerminate()");
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
//		MyToast.show("onTrimMemory()");
		super.onTrimMemory(level);
	}

	public class ActivityLifecycleCallbacksImpl implements ActivityLifecycleCallbacks {

		@Override
		public void onActivityCreated(Activity activity,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivityCreated():" + activity.getClass().getSimpleName());
			++MyApplication.this.mActivities;
		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivityDestroyed():" + activity.getClass().getSimpleName());
			if (--MyApplication.this.mActivities == 0) {
				MyApplication.this.unregisterComponentCallbacks(mComponentCallbacks);
				MyApplication.this.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
			}
		}

		@Override
		public void onActivityPaused(Activity activity) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivityPaused():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivityResumed(Activity activity) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivityResumed():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity,
				Bundle outState) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivitySaveInstanceState():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivityStarted(Activity activity) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivityStarted():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivityStopped(Activity activity) {
			// TODO Auto-generated method stub
//			MyToast.show("onActivityStopped():" + activity.getClass().getSimpleName());			
		}
	}

	public class ComponentCallbacksImpl implements ComponentCallbacks {

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			// TODO Auto-generated method stub
//			MyToast.show("ComponentCallbacks.onConfigurationChanged()");			
		}

		@Override
		public void onLowMemory() {
			// TODO Auto-generated method stub
//			MyToast.show("ComponentCallbacks.onLowMemory()");			
		}

	}

}
