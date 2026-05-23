@file:Suppress("unused", "PrivatePropertyName")

package cm.trixobase.library.common.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 21/05/26
 */

class MyNotification {

    private constructor(context: Context) {
        this.context = context
        this.manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val APPLICATION_NOTIFICATION_ID = 11177394

    private val context: Context
    private val manager: NotificationManager
    private lateinit var logo: Bitmap
    private lateinit var title: String
    private lateinit var content: String
    private var ticker: Int = 0
    private var time = 0L


    class Builder {

        internal constructor(context: Context) {
            this.instance = MyNotification(context)
            this.instance.title = context.getString(R.string.trixobase_enterprise)
            this.instance.content = "My notification in kotlin project."
            this.instance.time = System.currentTimeMillis() + 10000
            this.instance.ticker = R.drawable.iv_logo_ticker
            this.instance.logo = BitmapFactory
                .decodeResource(context.resources, R.drawable.iv_logo)
        }

        private var instance: MyNotification

        fun setText(title: String, content: String): Builder {
            this.instance.title = title
            this.instance.content = content
            return this
        }

        fun setPictures(logo: Int, ticker: Int): Builder {
            this.instance.logo = BitmapFactory
                .decodeResource(this.instance.context.resources, R.drawable.iv_logo)
            this.instance.ticker = ticker
            return this
        }

        fun setTime(time: Long): Builder {
            this.instance.time = time
            return this
        }

        fun buildChannel(id: String, name: String): Builder {
            this.instance.setChannel(id, name)
            return this
        }

        fun build(channelId: String) {
            this.instance.setNotification(channelId)
        }

    }

    companion object {
        fun builder(context: Context): Builder = Builder(context)
    }

    private fun setChannel(id: String, name: String) {
        try {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        } catch (e: Exception) {
            Log.d("Notification", "Build channel error: $e")
        }
    }

    private fun setNotification(channelId: String) {
        try {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val notification = NotificationCompat
                    .Builder(context, channelId)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setLargeIcon(logo)
                    .setSmallIcon(ticker)
                    .setAutoCancel(true)
                    .setShowWhen(true)
                    .setWhen(time)
                    .build()
                manager.notify(APPLICATION_NOTIFICATION_ID, notification)
            }
        } catch (e: Exception) {
            Log.d("Notification", "Build notification error: $e")
        }
    }
}