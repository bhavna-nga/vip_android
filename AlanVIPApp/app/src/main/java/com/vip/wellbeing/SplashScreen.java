package com.vip.wellbeing;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by standarduser on 27/05/16.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getIntent().getAction() == "FCM_NOTIFICATION") {

//                    Log.v("FCM_NOTIFICATION", "*************** "
//                            +"\n -- "+getIntent().getExtras().get("type")+"\n new msg "+ getIntent().getExtras().getString("new_message")
//                            +"\n msg "+getIntent().getExtras().getString("message"));

                    Bundle notificationBundle = getIntent().getExtras();

                    String messageContent = "", notificationType ="";

                    if(notificationBundle.getString("type") != null)
                        notificationType = notificationBundle.getString("type");

                    if (notificationBundle.getString("new_message") != null && !notificationBundle.getString("new_message").trim().equals(""))
                        messageContent = notificationBundle.getString("new_message");
                    else if (notificationBundle.getString("message") != null && !notificationBundle.getString("message").trim().equals(""))
                        messageContent = notificationBundle.getString("message");

                    handleNotificationClick(notificationType, messageContent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);
    }

    private void handleNotificationClick(String notificationType, String messageContent)
    {
        Intent intent;
        if (notificationType != null && notificationType.equalsIgnoreCase("video")) {

            intent = new Intent(this, VideoPlayActivity.class);
            intent.putExtra("videoUrl", messageContent);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
////            try {
////                contentIntent.send(this, 1, i);
////            } catch (PendingIntent.CanceledException e) {e.printStackTrace();}
//
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                    this)
//                    .setSmallIcon(R.drawable.vip_push)
//                    .setContentTitle(getString(R.string.app_name))
//
//                    .setStyle(
//                            new NotificationCompat.BigTextStyle().bigText(msg))
//                    .setContentText("check out this video").setAutoCancel(true)
//                    .setNumber(++counter);
//
//            mBuilder.setContentIntent(contentIntent);
//            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
        else if (notificationType != null && !notificationType.equalsIgnoreCase("chat")) {
//            PendingIntent contentIntent = PendingIntent.getActivity(
//                    this, 0, new Intent(this, MainActivity.class), 0);
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                    this)
//                    .setSmallIcon(R.drawable.vip_push)
//                    .setContentTitle(getString(R.string.app_name))
//                    .setStyle(
//                            new NotificationCompat.BigTextStyle().bigText(msg))
//                    .setContentText(msg).setAutoCancel(true)
//                    .setNumber(++counter);
//
//            mBuilder.setContentIntent(contentIntent);
//            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

            intent = new Intent(this, MainActivity.class);

        }
        else{
//            if (GlobalArrayList
//                    .isApplicationSentToBackground(getApplicationContext())) {

                MainActivity.getNewMessage = 1;
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("message", 1);

//                PendingIntent contentIntent = PendingIntent.getActivity(
//                        this.getApplicationContext(), 0, intentExtra, 0);

//                String messageContent = "";
//                if (extras.getString("new_message") != null && !extras.getString("new_message").trim().equals(""))
//                    messageContent = extras.getString("new_message");
//                else
//                    messageContent = extras.getString("message");

//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                        this)
//                        .setSmallIcon(R.drawable.vip_push)
//                        .setContentTitle(getString(R.string.app_name))
//                        .setStyle(
//                                new NotificationCompat.BigTextStyle()
//                                        .bigText(messageContent))
//                        .setContentText(messageContent)
//                        .setAutoCancel(true)
//                        .setDefaults(Notification.DEFAULT_SOUND);
//
//                mBuilder.setContentIntent(contentIntent);
//                mNotificationManager.notify(NOTIFICATION_ID,
//                        mBuilder.build());
                Log.d("", "Application is in background");
//            }
        }

//        Log.v("HANDLER", "***************** "+GlobalArrayList.mHandler);

        if (GlobalArrayList.mHandler != null) {

            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("pushMessage", 1);
            bundle.putString("newMessage", messageContent); //extras.getString("new_message"));
            message.setData(bundle);
            GlobalArrayList.mHandler.sendMessage(message);
        }

        startActivity(intent);
        finish();
    }
}
