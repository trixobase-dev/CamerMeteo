package cm.trixobase.library.common.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Powered by Trixobase Enterprise on 22/05/26
 */

@Entity(tableName = "t_notification")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "c_id")
    val id: Int,
    @ColumnInfo(name = "c_title")
    val title: String,
    @ColumnInfo(name = "c_content")
    val content: String,
    @ColumnInfo(name = "c_time")
    val time: Long,
)
