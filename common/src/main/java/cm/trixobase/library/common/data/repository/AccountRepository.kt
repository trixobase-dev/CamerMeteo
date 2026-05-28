@file:Suppress("unused")

package cm.trixobase.library.common.data.repository

import android.util.Log
import cm.trixobase.library.common.data.AppDatabase
import cm.trixobase.library.common.data.model.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/*
 * Powered by Trixobase Enterprise on 25/05/26
 */

class AccountRepository(db: AppDatabase) {

    private val dao = db.accountDao()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun fetchAll(): List<Account> {
        try {
            val entities = mutableListOf<Account>()
            val response = dao.fetchAll()
            response.value?.let { entities.addAll(it) }
            return entities
        } catch (e: Exception) {
            showLog(e.message!!)
            return listOf()
        }
    }

    fun fetchBy(id: Int): Account? {
        try {
            val response = dao.fetchBy(id)
            val entity = response.value
            return entity
        } catch (e: Exception) {
            showLog(e.message!!)
            return null
        }
    }

    fun add(entity: Account): Boolean {
        try {
            dao.add(entity)
            return true
        } catch (e: Exception) {
            showLog(e.message!!)
            return false
        }
    }

    fun edit(entity: Account): Boolean {
        try {
            dao.edit(entity)
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
        Log.e("AccountRepository", message)
    }

}