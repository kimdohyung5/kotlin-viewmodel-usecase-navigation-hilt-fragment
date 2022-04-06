package com.kimdo.flowsampleapp.presentation.coin_detail

import com.kimdo.flowsampleapp.domain.model.CoinDetail

data class CoinDetailState (
    val isLoading:Boolean = false,
    val coin: CoinDetail? = null,
    val error:String = ""
)