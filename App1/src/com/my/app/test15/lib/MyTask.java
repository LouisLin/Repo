package com.my.app.test15.lib;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Integer, Integer> {

	public interface DoInBackgroundCallback {
		abstract Integer doInBackground(String... params);
	}

	public interface OnTaskListener {
		abstract void onPreExecute();
		abstract void onPostExecute(Integer result);
		abstract void onProgressUpdate(Integer value);
		abstract void onCancelled(Integer result);
	}

	public interface OnResultListener {

		abstract void onResult(Integer result);

	}

	final public static int CANCELLED = -1;
	final public static int EXECUTE_FAILED = -2;
	final public static int INTERRUPTED = -3;
	final public static int TIMEOUT = -4;

	private	DoInBackgroundCallback mCallback;
	private OnTaskListener mTaskListener;

	public MyTask(DoInBackgroundCallback callback, OnTaskListener listener) {
		super();

		// TODO Auto-generated constructor stub
		mCallback = callback;
		mTaskListener = listener;
	}

	public MyTask() {
		super();
	}

	public void SetOnTaskListener(DoInBackgroundCallback callback, OnTaskListener listener) {
		mCallback = callback;
		mTaskListener = listener;
	}

	/*
	 * This step is normally used to setup the task,
	 * for instance by showing a progress bar in the user interface.
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		if (mTaskListener != null) {
			mTaskListener.onPreExecute();
		}

		super.onPreExecute();
	}

	/*
	 * This step is used to perform background computation
	 * that can take a long time.
	 * This step can also use publishProgress(Progress...)
	 * to publish one or more units of progress.
	 */
	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		Integer result = 0;

		if (mCallback != null) {
			result = mCallback.doInBackground(params);
		}

		return result;
	}

	/*
	 * This method is used to display any form of progress
	 * in the user interface while the background computation
	 * is still executing.
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		if (mTaskListener != null) {
			mTaskListener.onProgressUpdate(values[0]);
		}

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		if (mTaskListener != null) {
			mTaskListener.onPostExecute(result);
		}

		super.onPostExecute(result);
	}

	@Override
	protected void onCancelled(Integer result) {
		// TODO Auto-generated method stub
		if (mTaskListener != null) {
			mTaskListener.onCancelled(result);
		}

		super.onCancelled(result);
	}

	public void publishProgress(Integer value) {
		super.publishProgress(value);
	}

	public void getResult(final long timeout, final TimeUnit unit,
		final OnResultListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Integer result = -1;
				try {
					result = MyTask.this.get(timeout, unit);
				} catch (CancellationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = CANCELLED;
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = EXECUTE_FAILED;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = INTERRUPTED;
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = TIMEOUT;
					MyTask.this.cancel(true);
				} finally {
					if (listener != null) {
						listener.onResult(result);
					}
				}

			}
			
		}).start();		

	}

}
