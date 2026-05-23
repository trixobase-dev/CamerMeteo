package cm.trixobase.library.common.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cm.trixobase.library.common.data.model.Notification

/*
 * Powered by Trixobase Enterprise on 22/05/26
 */

@Dao
interface NotificationDao {

    @Query("SELECT * FROM t_notification")
    fun fetchAll(): LiveData<List<Notification>>

    @Query("SELECT * FROM t_notification WHERE c_id = :id")
    fun fetchBy(id: Int): LiveData<Notification>

    @Insert
    fun add(notification: Notification)

    @Update
    fun edit(notification: Notification)

    @Query("DELETE FROM t_notification WHERE c_id = :id")
    fun delete(id: Int)

}