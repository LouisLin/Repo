/**
 * 
 */
package com.my.app.test15.lib;

import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Louis
 *
 */
public class MyLayoutInflater {

	public static View inflate(int resource) {
        return LayoutInflater.from(MyApp.getContext()).inflate(resource, null);
	}
}
