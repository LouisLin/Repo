package com.my.app4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class App4Activity extends Activity implements OnClickListener {
	private Button mButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mButton = (Button)findViewById(R.id.button1);
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			MyIntent.startActivity(this, MyPrefActivity.class);
			MyToast.showLong(this, "Clicked!");
		}
	}
}