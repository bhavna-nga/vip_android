package com.vip.vipapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;


public class GcmMessageHandler extends IntentService {

    Bundle extras;
    private static String TAG = "GCM VIP ";
    public static final int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;
    int counter = 0;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        extras = intent.getExtras();

        Log.i(TAG, "Received : extra " + extras.toString());
//		Toast.makeText(getApplicationContext(), "Message:" + extras,
//				Toast.LENGTH_LONG).show();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        // mes = extras.getString("message");
        // showToast();
        Log.i(TAG,
                "Received : (" + messageType + ")  message "
                        + extras.getString("message") + "\n new msg " + extras.getString("new_message"));
        Log.i(TAG,
                "Received : (" + messageType + ") type "
                        + extras.getString("type"));

        if (!extras.isEmpty() || gcm == null) { // has effect of unparcelling
            // Bundle
            /*
             * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
            Log.i(TAG, "Not empty " + extras.toString());

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                // This loop represents the service doing some work.
                for (int i = 0; i < 2; i++) {
                    Log.i(TAG,
                            "Working... " + (i + 1) + "/5 @ "
                                    + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                try {
                    sendNotification(extras.getString("message"));
                } catch (Exception e) {
                    sendNotification(extras.getString(""));
                }
                Log.i(TAG, "Received: " + extras.toString());
            }
        } else {

            Log.i(TAG, "extra is empty ");

        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {

        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //Notification handler for videoActivity
        // !removed
        if (extras.getString("type") != null && extras.getString("type").equalsIgnoreCase("video")) {

            Intent i = new Intent(this, VideoPlayActivity.class);
            i.putExtra("videoUrl", msg);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

//            try {
//                contentIntent.send(this, 1, i);
//            } catch (PendingIntent.CanceledException e) {e.printStackTrace();}


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this)
                    .setSmallIcon(R.drawable.vip_push)
                    .setContentTitle(getString(R.string.app_name))

                    .setStyle(
                            new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText("check out this video").setAutoCancel(true)
                    .setNumber(++counter);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        } else if (extras.getString("type") != null && !extras.getString("type").equalsIgnoreCase("chat")) {
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this, MainActivity.class), 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this)
                    .setSmallIcon(R.drawable.vip_push)
                    .setContentTitle(getString(R.string.app_name))
                    .setStyle(
                            new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg).setAutoCancel(true)
                    .setNumber(++counter);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        } else {
            if (GlobalArrayList
                    .isApplicationSentToBackground(getApplicationContext())) {

                MainActivity.getNewMessage = 1;
                Intent intentExtra = new Intent(this, MainActivity.class);
                intentExtra.putExtra("message", 1);

                PendingIntent contentIntent = PendingIntent.getActivity(
                        this.getApplicationContext(), 0, intentExtra, 0);

                String messageContent = "";
                if (extras.getString("new_message") != null && !extras.getString("new_message").trim().equals(""))
                    messageContent = extras.getString("new_message");
                else
                    messageContent = extras.getString("message");

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        this)
                        .setSmallIcon(R.drawable.vip_push)
                        .setContentTitle(getString(R.string.app_name))
                        .setStyle(
                                new NotificationCompat.BigTextStyle()
                                        .bigText(messageContent))
                        .setContentText(messageContent)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND);

                mBuilder.setContentIntent(contentIntent);
                mNotificationManager.notify(NOTIFICATION_ID,
                        mBuilder.build());
                Log.d("", "Aplication is in background");
            } else {
                Log.d("", "Aplication is in foreground");
            }
            if (GlobalArrayList.mHandler != null) {

                String messageContent = "";
                if (extras.getString("new_message") != null && !extras.getString("new_message").trim().equals(""))
                    messageContent = extras.getString("new_message");
                else
                    messageContent = extras.getString("message");

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("pushMessage", 1);
                bundle.putString("newMessage", messageContent); //extras.getString("new_message"));
                message.setData(bundle);
                GlobalArrayList.mHandler.sendMessage(message);
            }
        }

    }

}