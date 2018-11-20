package com.vip.vipapp.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
    }

    /* ==================================== OnClick Methods ===================================== */


    /* ================================== User Define Method ==================================== */


    /* ==================================== Inner Classes ======================================  */
}

