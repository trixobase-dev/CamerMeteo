@file:Suppress("DEPRECATION")

package cm.trixobase.camermeteo.backend

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

object Manager {

    fun get(context: Context, key: String, defaultValue: String): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue)
            ?: defaultValue
    }

    fun get(context: Context, key: String, defaultValue: Int): Int {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue)
    }

    fun get(context: Context, key: String, defaultValue: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue)
    }

    fun set(context: Context, key: String, value: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putString(key, value)
        }
    }

    fun set(context: Context, key: String, value: Int) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putInt(key, value)
        }
    }

    fun set(context: Context, key: String, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putBoolean(key, value)
        }
    }

}