package com.example.gamegivewayapp.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamegivewayapp.model.Giveaways

class DatabaseRepositoryImpl(
    private val dao: GiveawaysDao
) : DatabaseRepository {
    override suspend fun insertGiveaways(giveaways: List<Giveaways>) =
        dao.insertGiveaways(giveaways)

    override suspend fun getAllGiveaways(): List<Giveaways> =
        dao.getAllGiveaways()

    override suspend fun getGiveawaysById(id: Int): Giveaways =
        dao.getGiveawaysById(id)

    override suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways> =
        dao.getGiveawaysByPlatform(platform)

    override suspend fun deleteGiveaway(giveaways: List<Giveaways>) =
        dao.deleteGiveaway(giveaways)

}

interface DatabaseRepository {
    suspend fun insertGiveaways(giveaways: List<Giveaways>)
    suspend fun getAllGiveaways() : List<Giveaways>
    suspend fun getGiveawaysById(id: Int) : Giveaways
    suspend fun getGiveawaysByPlatform(platform: String) : List<Giveaways>
    suspend fun deleteGiveaway(giveaways: List<Giveaways>)
}