package com.my.app.test2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class App2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNum = manager.getLine1Number();
        TextView view2 = (TextView)findViewById(R.id.textView2);
        view2.setText(phoneNum);

        String simNum = manager.getSimSerialNumber();
        TextView view4 = (TextView)findViewById(R.id.textView4);
        view4.setText(simNum);
        
        String imei = manager.getDeviceId();
        TextView view6 = (TextView)findViewById(R.id.textView6);
        view6.setText(imei);
        
        String out = CallLog.Calls.getLastOutgoingCall(this);
        TextView view8 = (TextView)findViewById(R.id.textView8);
        view8.setText(out);
    }
}