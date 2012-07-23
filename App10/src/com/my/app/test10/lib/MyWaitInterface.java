package com.my.app.test10.lib;

public interface MyWaitInterface {

	public static interface OnCompareListener {

		abstract boolean isDone();

	}

	public static interface OnTimeoutListener {

		abstract void cancel();

	}

}
