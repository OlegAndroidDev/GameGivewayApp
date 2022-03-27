package com.example.gamegivewayapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamegivewayapp.database.DatabaseRepository
import com.example.gamegivewayapp.model.Giveaways
import com.example.gamegivewayapp.rest.GiveawayApiRepository
import com.example.gamegivewayapp.utils.GiveawayState
import com.example.gamegivewayapp.utils.NewPlatformType
import com.example.gamegivewayapp.utils.PlatformType
import com.example.gamegivewayapp.utils.SortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GiveawaysViewModel(
    private val giveawayApiRepository: GiveawayApiRepository,
    private val databaseRepository: DatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val coroutineScope: CoroutineScope = CoroutineScope(ioDispatcher)
) : ViewModel() {

    private val _sortedGiveaways: MutableLiveData<GiveawayState> =
        MutableLiveData(GiveawayState.LOADING)

    val giveaways : LiveData<GiveawayState> get() = _sortedGiveaways

    var platform : PlatformType = PlatformType.ANDROID

    var databasePlatform : NewPlatformType = NewPlatformType.PS4

    var giveaway : Giveaways? = null

    fun getSortedGiveaways(sortBy: SortType = SortType.DATE) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = giveawayApiRepository.getAllGiveaways(sortBy)
                if (response.isSuccessful) {
                    response.body()?.let {
                        databaseRepository.insertGiveaways(it)
                        val localData = databaseRepository.getAllGiveaways()
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData))
                    } ?: throw Exception("Response is null")
                }
                else {
                    throw Exception("Unsuccessful response")
                }
            }
            catch (e: Exception) {
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
                loadAllFromDB()
            }
        }
    }

    private suspend fun loadAllFromDB() {
        try {
            val localData = databaseRepository.getAllGiveaways()
            _sortedGiveaways.postValue(GiveawayState.SUCCESS(
            localData, true
        )) }
        catch (e: Exception) {
            _sortedGiveaways.postValue(GiveawayState.ERROR(e))
        }
    }

    fun getGiveawaysByPlatform() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = giveawayApiRepository.getGiveawaysByPlatform(platform)
                if (response.isSuccessful) {
                    response.body()?.let {
                        databaseRepository.insertGiveaways(it)
                        val localDataPlatform = databaseRepository
                            .getGiveawaysByPlatform(databasePlatform.realValue)
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(localDataPlatform))
                    } ?: throw Exception("Response is null")
                }
                else {
                    throw Exception("Unsuccessful response")
                }
            }
            catch (e: Exception) {
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
                loadByPlatformsFromDB(databasePlatform)
            }
        }
    }

    private suspend fun loadByPlatformsFromDB(platform: NewPlatformType) {
        try {
            val localData = databaseRepository.getGiveawaysByPlatform(platform.realValue)
            _sortedGiveaways.postValue(GiveawayState.SUCCESS(
                localData, true
            )) }
        catch (e: Exception) {
            _sortedGiveaways.postValue(GiveawayState.ERROR(e))
        }
    }
}