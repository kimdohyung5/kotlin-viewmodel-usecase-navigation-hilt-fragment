package com.kimdo.flowsampleapp.presentation.coin_list

import com.kimdo.flowsampleapp.domain.model.Coin

data class CoinListState (
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)


