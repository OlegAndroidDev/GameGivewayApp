package com.example.gamegivewayapp.di

import android.content.Context
import androidx.room.Room
import com.example.gamegivewayapp.database.DatabaseRepository
import com.example.gamegivewayapp.database.DatabaseRepositoryImpl
import com.example.gamegivewayapp.database.GiveawaysDao
import com.example.gamegivewayapp.database.GiveawaysDatabase
import com.example.gamegivewayapp.rest.GiveawayApi
import com.example.gamegivewayapp.rest.GiveawayApiRepository
import com.example.gamegivewayapp.rest.GiveawayApiRepositoryImpl
import com.example.gamegivewayapp.viewmodel.GiveawaysViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun providesGson() = Gson()

    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


    fun providesGiveawayApi(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(GiveawayApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GiveawayApi::class.java)


    fun providesGiveawayRepo(giveawayApi: GiveawayApi) : GiveawayApiRepository =
        GiveawayApiRepositoryImpl(giveawayApi)

    single { providesGiveawayRepo(get()) }
    single { providesLoggingInterceptor() }
    single { providesOkHttpClient(get()) }
    single { providesGiveawayApi(get()) }
}

val applicationModule = module {

    fun providesGiveawaysDao(giveawaysDatabase: GiveawaysDatabase) : GiveawaysDao =
        giveawaysDatabase.getGiveawaysDao()

    fun providesGiveawayDatabaseRepo(giveawaysDao: GiveawaysDao) : DatabaseRepository =
        DatabaseRepositoryImpl(giveawaysDao)

    fun providesGiveawaysDatabase(context: Context) : GiveawaysDatabase =
        Room.databaseBuilder(
            context,
            GiveawaysDatabase::class.java,
            "giveaways-db"
        ).build()

    single { providesGiveawaysDatabase(androidContext()) }
    single { providesGiveawaysDao(get()) }
    single { providesGiveawayDatabaseRepo(get()) }
    viewModel { GiveawaysViewModel(get(), get()) }
}