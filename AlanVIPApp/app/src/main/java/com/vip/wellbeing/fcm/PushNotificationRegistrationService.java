package com.vip.wellbeing.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.vip.wellbeing.GlobalArrayList;
import com.vip.wellbeing.SendIntialInfo;

/**
 * This class/service is used to handle the device token for send it to server.
 */

public class PushNotificationRegistrationService extends FirebaseInstanceIdService {

    /* ================================== Constant Variable ===================================== */

    // Tag for the Log
    private final static String TAG = PushNotificationRegistrationService.class.getSimpleName();

    /* =================================== Class Variable ======================================= */

    /* ================================== Life Cycle Method ===================================== */

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    /* ================================== User Define Method ==================================== */

    /**
     * Persist token to servers.
     *
     * @param token The new token.
     */
    private static void sendRegistrationToServer(String token) {
        // TODO: Send token to server.
        GlobalArrayList.regId = token;
//        Log.v("SEND", "**************** token "+token +" \n client ID "+GlobalArrayList.CLIENT_ID);
        new SendIntialInfo(token, GlobalArrayList.CLIENT_ID);
    }

    /* ==================================== Inner Classes ======================================  */

}
