package com.vip.vipapp;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver  extends WakefulBroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {

	    	String regId = intent.getExtras().getString("registration_id"); 
	    	Log.d("","regId:"+regId);
	        // Explicitly specify that GcmMessageHandler will handle the intent.
	        ComponentName comp = new ComponentName(context.getPackageName(),
	                GcmMessageHandler.class.getName());

	        // Start the service, keeping the device awake while it is launching.
	        startWakefulService(context, (intent.setComponent(comp)));
	        setResultCode(Activity.RESULT_OK);
	    }
	}

