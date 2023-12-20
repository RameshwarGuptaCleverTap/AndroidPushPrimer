package com.clevertapTestAll

//import android.support.v4.app.
import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.Constants
import com.clevertap.android.sdk.PushPermissionResponseListener
import com.clevertap.android.sdk.inapp.CTLocalInApp
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener
import com.clevertapTestAll.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(), PushPermissionResponseListener, CTPushNotificationListener {

    var cleverTapDefaultInstance: CleverTapAPI? = null
    private lateinit var requestLauncher: ActivityResultLauncher<String>
    lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {


      super.onCreate(savedInstanceState)

      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      requestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          }
        } else {
          //show error message
          Toast.makeText(
            applicationContext,
            "Enable Notification To Receive Notifications",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
      cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)
      cleverTapDefaultInstance!!.enableDeviceNetworkInfoReporting(true)
      CleverTapAPI.setDebugLevel(3)
      cleverTapDefaultInstance?.ctPushNotificationListener = this;
      cleverTapDefaultInstance?.registerPushPermissionNotificationResponseListener(this)


      val btn = findViewById<Button>(R.id.button_id)
        btn.setOnClickListener {
          if (!cleverTapDefaultInstance?.isPushPermissionGranted!!){

            askForNotificationPermission()
            Toast.makeText(applicationContext, "Enable Notification", Toast.LENGTH_SHORT).show()
          }
        }


//        val stickyNotificationBtn = findViewById<Button>(R.id.stickyNotification)
//        stickyNotificationBtn.setOnClickListener {
//                cleverTapDefaultInstance?.pushEvent("sticky_notification")
//        }
//
//        val autoCar = findViewById<Button>(R.id.autoCaraousal)
//        autoCar.setOnClickListener {
//            cleverTapDefaultInstance?.pushEvent("auto_Caraousal")
//        }
//
//
//        onUserLoginMethod()
    }

  
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /**
         * On Android 12, Raise notification clicked event when Activity is already running in activity backstack
         */
//        CleverTapAPI.getDefaultInstance(applicationContext)!!
//            .pushNotificationClickedEvent(intent!!.extras)
//        val extras = intent.extras
//        val newURL = extras?.get("wzrk_dl").toString()
//        val intents = Intent(this, WebActivity::class.java)
//        intents.putExtra("webURL", newURL)
//        startActivity(intents)
//        finish()
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askForNotificationPermission() {
        //requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

      val jsonObject = CTLocalInApp.builder()
        .setInAppType(CTLocalInApp.InAppType.HALF_INTERSTITIAL)
        .setTitleText("Get Notified \uD83D\uDD14")
        .setMessageText("Please enable notifications on your device to use Push Notifications.")
        .followDeviceOrientation(true)
        .setPositiveBtnText("Allow")
        .setNegativeBtnText("Cancel")
        .build()
      cleverTapDefaultInstance?.promptPushPrimer(jsonObject)
    }


    override fun onPushPermissionResponse(accepted: Boolean) {
      Log.d("Clevertap", "onPushPermissionResponse :  InApp---> response() called accepted=$accepted")
      if (accepted) {
          Log.d("TAG", "push permission allowed: ")
            CleverTapAPI.createNotificationChannel(getApplicationContext(), "xiaominew", "CT-PushAndroidNew",
                "Test-NotificationsAndroid", NotificationManager.IMPORTANCE_HIGH, true);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cleverTapDefaultInstance?.unregisterPushPermissionNotificationResponseListener(this)
    }

    private fun onUserLoginMethod() {

        val profileUpdate = HashMap<String, Any>()
        profileUpdate["Name"] = "Rameshwar Gupta" // String
        profileUpdate["Identity"] = 1122252021 // String or number
        profileUpdate["Email"] = "priyanka.papola16feb@gmail.com" // Email address of the user
        profileUpdate["Phone"] = "+919599721544" // Phone (with the country code, starting with +)
        profileUpdate["Gender"] = "M" // Can be either M or F
        profileUpdate["DOB"] = Date() // Date of Birth. Set the Date object to the appropriate value first
        profileUpdate["Photo"] = "www.foobar.com/image.jpeg" // URL to the Image

        profileUpdate["MSG-email"] = false // Disable email notifications
        profileUpdate["MSG-push"] = true // Enable push notifications
        profileUpdate["MSG-sms"] = false // Disable SMS notifications
        profileUpdate["MSG-dndPhone"] = true // Opt out phone
        profileUpdate["MSG-dndEmail"] = true // Opt out email
        profileUpdate["MyStuff"] = arrayListOf("bag", "shoes") //ArrayList of Strings
        profileUpdate["MyStuff"] = arrayOf("Jeans", "Perfume") //String Array
        cleverTapDefaultInstance?.onUserLogin(profileUpdate)
    }


    fun sendEventsOnProfile() {

        val prodViewedAction = mapOf(
            "Product Name" to "Casio Chronograph Watch",
            "Category" to "Mens Accessories",
            "Price" to 59.99,
            "Date" to Date()
        )
        cleverTapDefaultInstance?.pushEvent("Product viewed", prodViewedAction)
    }



    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>?) {
        Log.d("TAG", "onNotificationClickedPayloadReceived: ")
    }

}
