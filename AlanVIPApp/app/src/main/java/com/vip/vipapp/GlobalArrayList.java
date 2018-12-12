package com.vip.vipapp;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;


public class GlobalArrayList {
	static int userId = 0;
//	static int clientId1 = 479; // Change From ExcelSheet
	static String auth_token = "";
	public static String regId = "";
	static Handler mHandler;
	static boolean isShowChat = false;
	static boolean isNotificationMessage = false;
	static ArrayList<EachMessage> message,message2;
//	static String BASE_URL = "http://2ndcousinmedia.com/";
	static String BASE_URL = "http://10.0.0.156:80/";//"http://192.168.1.18:80/"; //http://planning.2ndcousinmedia.com:8000
	public static String CLIENT_ID;

	static String MSG_TYPE_BLOG = "3",
			MSG_TYPE_VIDEO = "4",
			MSG_TYPE_AUDIO = "5",
			MSG_TYPE_SPECIAL = "6",
			MSG_TYPE_TEXT = "7",
			VIDEO_TYPE_GENERAL = "1",
			VIDEO_TYPE_VIMEO = "2",
			VIDEO_TYPE_YOUTUBE = "3",
			VIDEO_TYPE_IFRAME = "4";

	public static void setClientID(String clientId)
	{
		CLIENT_ID = clientId;
	}
	/**
	   * Checks if the application is being sent in the background (i.e behind
	   * another application's Activity).
	   * 
	   * @param context the context
	   * @return <code>true</code> if another application will be above this one.
	   */
	  public static boolean isApplicationSentToBackground(Context context) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> tasks = am.getRunningTasks(1);
	    if (!tasks.isEmpty()) {
	      ComponentName topActivity = tasks.get(0).topActivity;
	      if (!topActivity.getPackageName().equals(context.getPackageName())) {
	        return true;
	      }
	    }

	    return false;
	  }
}
