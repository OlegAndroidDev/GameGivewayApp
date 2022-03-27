package com.example.gamegivewayapp.utils

import com.example.gamegivewayapp.model.Giveaways

sealed class GiveawayState {
    object LOADING : GiveawayState()
    class SUCCESS<T>(val giveaways : T, isLocalData : Boolean = false) : GiveawayState()
    class ERROR(val error: Throwable) : GiveawayState()
}
