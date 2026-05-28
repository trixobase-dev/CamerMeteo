@file:Suppress("ConstPropertyName")

package cm.trixobase.camermeteo.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import cm.trixobase.camermeteo.ui.view.MainActivity
import cm.trixobase.library.common.utils.NotificationProcess

/*
 * Powered by Trixobase Enterprise on 28/05/26
 */

class MyService : Service() {

    private var notificationManager: NotificationManager? = null

    companion object {

        var instance: MyService? = null
        var isRunning: Boolean = false

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        instance = this
        isRunning = true
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val pendingIntent =
            PendingIntent.getActivity(
                this, 0, Intent(this, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationProcess.builder(this).build(pendingIntent)

        startForeground(NotificationProcess.APPLICATION_NOTIFICATION_ID, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        instance = null
        notificationManager?.cancel(NotificationProcess.APPLICATION_NOTIFICATION_ID)
        super.onDestroy()
    }

}