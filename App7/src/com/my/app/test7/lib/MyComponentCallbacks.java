package com.my.app.test7.lib;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;

public class MyComponentCallbacks implements ComponentCallbacks {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
//		MyToast.show("ComponentCallbacks.onConfigurationChanged()");			
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
//		MyToast.show("ComponentCallbacks.onLowMemory()");			
	}

}
