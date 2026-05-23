@file:Suppress("unused")

package cm.trixobase.library.common.data.repository

import android.util.Log
import cm.trixobase.library.common.data.AppDatabase
import cm.trixobase.library.common.data.model.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/*
 * Powered by Trixobase Enterprise on 22/05/26
 */

class NotificationRepository(db: AppDatabase) {

    private val dao = db.notificationDao()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun fetchAll(): List<Notification> {
        try {
            val entities = mutableListOf<Notification>()
            val response = dao.fetchAll()
            response.value?.let { entities.addAll(it) }
            return entities
        } catch (e: Exception) {
            showLog(e.message!!)
            return listOf()
        }
    }

    fun fetchBy(id: Int): Notification? {
        try {
            val response = dao.fetchBy(id)
            val entity = response.value
            return entity
        } catch (e: Exception) {
            showLog(e.message!!)
            return null
        }
    }

    fun add(notification: Notification): Boolean {
        try {
            dao.add(notification)
            return true
        } catch (e: Exception) {
            showLog(e.message!!)
            return false
        }
    }

    fun edit(notification: Notification): Boolean {
        try {
            dao.edit(notification)
            return true
        } catch (e: Exception) {
            showLog(e.message!!)
            return false
        }
    }

    fun delete(id: Int): Boolean {
        try {
            dao.delete(id)
            return true
        } catch (e: Exception) {
            showLog(e.message!!)
            return false
        }
    }

    private fun showLog(message: String) {
        Log.e("NotificationRepository", message)
    }

}