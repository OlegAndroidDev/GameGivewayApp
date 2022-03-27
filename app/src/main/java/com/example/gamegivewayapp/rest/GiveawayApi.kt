package com.example.gamegivewayapp.rest

import com.example.gamegivewayapp.model.Giveaways
import com.example.gamegivewayapp.utils.SortType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiveawayApi {

    @GET(GIVEAWAYS_ENDPOINT)
    suspend fun getAllGiveaways(
        @Query("sort-by") orderBy : String
    ) : Response<List<Giveaways>>

    @GET(GIVEAWAYS_ENDPOINT)
    suspend fun getGiveawaysByPlatform(
        @Query("platform") platform : String
    ) : Response<List<Giveaways>>

    companion object {
        const val BASE_URL = "https://www.gamerpower.com/api/"
        private const val SORT_BY_DATE = "date"

        //can be sorted by date, value, or popularity
        private const val GIVEAWAYS_ENDPOINT = "giveaways"
    }
}