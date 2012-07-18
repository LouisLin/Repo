package com.my.app.test1.lib;

public class MyWait {

	public static void wait(final int sec) {
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
					}
//					if (mTask.getStatus() == AsyncTask.Status.FINISHED) {
//						break;
//					}
				}
				if (i >= sec) {
//					mTask.cancel(true);
				}
				
			}
			
		}).start();		
	}
}
