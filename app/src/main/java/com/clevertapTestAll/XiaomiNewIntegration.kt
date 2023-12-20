package com.clevertapTestAll

import android.app.Application
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler

class XiaomiNewIntegration : Application() {

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()
        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler)
    }

}