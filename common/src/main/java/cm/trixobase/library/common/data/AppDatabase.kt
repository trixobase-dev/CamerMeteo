package cm.trixobase.library.common.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cm.trixobase.library.common.data.datasource.NotificationDao
import cm.trixobase.library.common.data.model.Notification

/*
 * Powered by Trixobase Enterprise on 22/05/26
 */

@Database(entities = [(Notification::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun notificationDao(): NotificationDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context = context.applicationContext,
                            klass = AppDatabase::class.java,
                            name = "db_trixobase")
                        .fallbackToDestructiveMigration(false)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}