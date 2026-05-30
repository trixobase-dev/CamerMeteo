@file:Suppress("unused", "PropertyName")

package cm.trixobase.library.common.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.content.ContextCompat
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 21/05/26
 */

class NotificationProcess {

    private constructor(context: Context) {
        this.context = context
        this.manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val context: Context
    private val manager: NotificationManager
    private lateinit var logo: Bitmap
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var tickerTitle: String
    private var tickerPicture: Int = 0
    private var time = 0L


    class Builder {

        internal constructor(context: Context) {
            this.instance = NotificationProcess(context)
            this.instance.title = "Trixobase Enterprise"
            this.instance.content = "My notification in kotlin project."
            this.instance.time = System.currentTimeMillis()
            this.instance.tickerTitle = "By trixobase"
            this.instance.tickerPicture = R.drawable.iv_logo_ticker
            this.instance.logo = PictureProcess.convert(context, R.drawable.iv_logo)
        }

        private var instance: NotificationProcess

        fun setText(tickerTitle: String, title: String, content: String): Builder {
            this.instance.tickerTitle = tickerTitle
            this.instance.title = title
            this.instance.content = content
            return this
        }

        fun setPictures(logo: Int, tickerPicture: Int): Builder {
            this.instance.logo = PictureProcess.convert(instance.context, logo)
            this.instance.tickerPicture = tickerPicture
            return this
        }

        fun setTime(time: Long): Builder {
            this.instance.time = time
            return this
        }

        fun build(pendingIntent: PendingIntent, channelId: String = "trixobase_channel_id"): Notification {
            return instance.getNotification(pendingIntent, channelId)
        }

        fun setChannel(id: String, name: String, description: String = "By Trixobase Enterprise"): Builder {
            this.instance.setChannel(id, name, description)
            return this
        }

        fun notify(channelId: String, pendingIntent: PendingIntent) {
            this.instance.setNotification(pendingIntent, channelId)
        }

    }

    companion object {

        const val APPLICATION_NOTIFICATION_ID: Int = 11177394

        fun builder(context: Context): Builder = Builder(context)
    }

    private fun setChannel(id: String, name: String, description: String) {
        try {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = description
            manager.createNotificationChannel(channel)
        } catch (e: Exception) {
            showLog("setChannel", e.message!!)
        }
    }

    private fun setNotification(pendingIntent: PendingIntent, channelId: String) {
        try {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val notification = getNotification(pendingIntent, channelId)
                manager.notify(APPLICATION_NOTIFICATION_ID, notification)
            }
        } catch (e: Exception) {
            showLog("setNotification", e.message!!)
        }
    }

    private fun getNotification(pendingIntent: PendingIntent, channelId: String): Notification {
        return NotificationCompat
            .Builder(context, channelId)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntent)
            .setTicker(tickerTitle)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(PictureProcess.convert(context, R.drawable.iv_icon_eye))
            .setSmallIcon(tickerPicture)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(false)
            .setWhen(time)
            .build()
    }

    private fun showLog(method: String, message: String) {
        Utils.process.showLog(this, method, message)
    }

}