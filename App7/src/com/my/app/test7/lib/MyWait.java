package com.my.app.test7.lib;

import com.my.app.test7.lib.MyWaitInterface.OnCompareListener;
import com.my.app.test7.lib.MyWaitInterface.OnTimeoutListener;

public class MyWait {

	public static void wait(final int sec,
		final OnCompareListener compareListener,
		final OnTimeoutListener timeoutListener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int i;

				for (i = 0; i < sec; ++i) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if (timeoutListener != null) {
							timeoutListener.cancel();
						}
						return;
					}
					if (compareListener != null && compareListener.isDone()) {
//					if (mTask.getStatus() == AsyncTask.Status.FINISHED) {
						break;
					}
				}
				if (i >= sec) {
					if (timeoutListener != null) {
						timeoutListener.cancel();
					}
//					mTask.cancel(true);
				}
				
			}
			
		}).start();		
	}
}
