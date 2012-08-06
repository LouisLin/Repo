package com.my.app.test7.lib;

public interface MyWaitInterface {

	public static interface OnCompareListener {

		abstract boolean isDone();

	}

	public static interface OnTimeoutListener {

		abstract void cancel();

	}

}
