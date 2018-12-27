package com.vip.wellbeing.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vip.wellbeing.MainActivity;

/**
 * This class/service is used to handle the coming notification when app in foreground.
 */

public class PushNotificationService extends FirebaseMessagingService {

    /* ================================== Constant Variable ===================================== */


    /* =================================== Class Variable ======================================= */


    /* ================================== Life Cycle Method ===================================== */


    /* ============================= Implemented Interface Method =============================== */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        Log.v("MSG", "REC ********************** "+ remoteMessage.getData().get("type"));

//        if(remoteMessage.getData().get("type").equalsIgnoreCase("chat")) {
//            MainActivity mainActivity = new MainActivity();
//            mainActivity.reloadChatMessages();
//        }
    }

    /* ==================================== OnClick Methods ===================================== */


    /* ================================== User Define Method ==================================== */


    /* ==================================== Inner Classes ======================================  */
}

