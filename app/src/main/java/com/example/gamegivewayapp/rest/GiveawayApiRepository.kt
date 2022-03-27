package com.example.gamegivewayapp.rest

import com.example.gamegivewayapp.model.Giveaways
import com.example.gamegivewayapp.utils.PlatformType
import com.example.gamegivewayapp.utils.SortType
import retrofit2.Response

class GiveawayApiRepositoryImpl (
    private val giveawayApi: GiveawayApi
) : GiveawayApiRepository {

    override suspend fun getAllGiveaways(orderBy: SortType): Response<List<Giveaways>> =
        giveawayApi.getAllGiveaways(orderBy.realValue)

    override suspend fun getGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>> =
        giveawayApi.getGiveawaysByPlatform(platform.realValue)
}

interface GiveawayApiRepository {
    suspend fun getAllGiveaways(orderBy: SortType) : Response<List<Giveaways>>
    suspend fun getGiveawaysByPlatform(platform: PlatformType) : Response<List<Giveaways>>
}