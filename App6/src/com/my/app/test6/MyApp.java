/**
 * 
 */
package com.my.app.test6;

import com.my.app.test6.lib.MyToast;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * @author Louis
 *
 */
public class MyApp extends Application {
	private ActivityLifecycleCallbacks mActivityLifecycleCallbacks =
		new ActivityLifecycleCallbacksImpl();
	private ComponentCallbacks mComponentCallbacks =
		new ComponentCallbacksImpl();
	public int mActivities = 0;
	private static Context mAppContext = null;

	public static Context getContext() {
		return mAppContext;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		MyToast.show(this, "onConfigurationChanged()");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mAppContext = this;
		MyToast.show(this, "onCreate()");
		registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
		registerComponentCallbacks(mComponentCallbacks );
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		MyToast.show(this, "onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		MyToast.show(this, "onTerminate()");
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		MyToast.show(this, "onTrimMemory()");
		super.onTrimMemory(level);
	}

	public class ActivityLifecycleCallbacksImpl implements ActivityLifecycleCallbacks {

		@Override
		public void onActivityCreated(Activity activity,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivityCreated():" + activity.getClass().getSimpleName());
			++MyApp.this.mActivities;
		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivityDestroyed():" + activity.getClass().getSimpleName());
			if (--MyApp.this.mActivities == 0) {
				MyApp.this.unregisterComponentCallbacks(mComponentCallbacks);
				MyApp.this.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
			}
		}

		@Override
		public void onActivityPaused(Activity activity) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivityPaused():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivityResumed(Activity activity) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivityResumed():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity,
				Bundle outState) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivitySaveInstanceState():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivityStarted(Activity activity) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivityStarted():" + activity.getClass().getSimpleName());			
		}

		@Override
		public void onActivityStopped(Activity activity) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "onActivityStopped():" + activity.getClass().getSimpleName());			
		}
	}

	public class ComponentCallbacksImpl implements ComponentCallbacks {

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "ComponentCallbacks.onConfigurationChanged()");			
		}

		@Override
		public void onLowMemory() {
			// TODO Auto-generated method stub
			MyToast.show(MyApp.this, "ComponentCallbacks.onLowMemory()");			
		}

	}

}
