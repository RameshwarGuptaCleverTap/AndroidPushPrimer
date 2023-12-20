package com.clevertapTestAll;

import static com.xiaomi.push.db.t;

import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.Constants;
import com.clevertap.android.sdk.Logger;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
//import com.xiaomi.mipush.sdk.MiPushMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;




public class MYFCM extends FirebaseMessagingService {

        public static final String NOTIFICATION_CHANNEL_ID = "CT1104";


    @Override
        public void onNewToken(@NonNull @NotNull String refreshedToken) {
            super.onNewToken(refreshedToken);
        }

        @Override
        public void onMessageReceived(RemoteMessage message) {
            new CTFcmMessageHandler().createNotification(getApplicationContext(), message);
        }

}