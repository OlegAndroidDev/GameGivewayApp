package com.example.gamegivewayapp.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.gamegivewayapp.model.Giveaways

@Database(
    entities = [Giveaways::class],
    version = 1
)
abstract class GiveawaysDatabase : RoomDatabase(){
    abstract fun getGiveawaysDao() : GiveawaysDao
}

@Dao
interface GiveawaysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertGiveaways(giveaways: List<Giveaways>)

    @Query("SELECT * FROM giveaways")
    suspend fun getAllGiveaways() : List<Giveaways>

    @Query("SELECT * FROM giveaways WHERE id LIKE (:id) LIMIT 1")
    suspend fun getGiveawaysById(id: Int) : Giveaways

    @Query("SELECT * FROM  giveaways WHERE platforms LIKE '%' || :platform || '%'")
    suspend fun getGiveawaysByPlatform(platform: String) : List<Giveaways>

    @Delete
    suspend fun deleteGiveaway(giveaways: List<Giveaways>)
}