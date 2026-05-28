package cm.trixobase.library.common.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Powered by Trixobase Enterprise on 25/05/26
 */

@Entity(tableName = "t_account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "c_id")
    val id: Int,
    @ColumnInfo(name = "c_firstname")
    val firstname: String,
    @ColumnInfo(name = "c_lastname")
    val lastname: String,
    @ColumnInfo(name = "c_username")
    val username: Long,
)
