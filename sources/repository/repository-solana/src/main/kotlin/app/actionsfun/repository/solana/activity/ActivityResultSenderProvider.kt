package app.actionsfun.repository.solana.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender

class ActivityResultSenderProvider(application: Application) {

    var activityResultSender: ActivityResultSender? = null

    init {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityResultSender = ActivityResultSender(activity as AppCompatActivity)
            }
            override fun onActivityDestroyed(activity: Activity) {
                activityResultSender = null
            }
            override fun onActivityResumed(activity: Activity) = Unit
            override fun onActivityPaused(activity: Activity) = Unit
            override fun onActivityStarted(activity: Activity) = Unit
            override fun onActivityStopped(activity: Activity) = Unit
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
        })
    }
}