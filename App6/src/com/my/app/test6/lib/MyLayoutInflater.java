/**
 * 
 */
package com.my.app.test6.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Louis
 *
 */
public class MyLayoutInflater {

	public static View inflate(Context context, int resource) {
        return LayoutInflater.from(context).inflate(resource, null);
	}
}
