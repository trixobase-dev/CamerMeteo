package cm.trixobase.library.common.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cm.trixobase.library.common.data.model.Account

/*
 * Powered by Trixobase Enterprise on 25/05/26
 */

@Dao
interface AccountDao {

    @Query("SELECT * FROM t_account")
    fun fetchAll(): LiveData<List<Account>>

    @Query("SELECT * FROM t_account WHERE c_id = :id")
    fun fetchBy(id: Int): LiveData<Account>

    @Insert
    fun add(entity: Account)

    @Update
    fun edit(entity: Account)

    @Query("DELETE FROM t_account WHERE c_id = :id")
    fun delete(id: Int)

}