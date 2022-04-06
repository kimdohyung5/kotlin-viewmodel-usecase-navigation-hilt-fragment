package com.kimdo.flowsampleapp.presentation.coin_detail

import android.util.Log
import androidx.lifecycle.*
import com.kimdo.flowsampleapp.common.Constants
import com.kimdo.flowsampleapp.common.Resource
import com.kimdo.flowsampleapp.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val useCase: GetCoinUseCase) : ViewModel(){

    private val _state = MutableLiveData(CoinDetailState())
    val state : LiveData<CoinDetailState> = _state


    fun getCoin(coinId: String ) {

        Log.d(TAG, "getCoin: $coinId")

        useCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinDetailState( coin = result.data )
                }
                is Resource.Error -> {
                    _state.value = CoinDetailState( error = result.message ?: "" )
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState( isLoading = true )
                }
            }
        }.launchIn( viewModelScope )
    }

    companion object {
        val TAG = "CoinDetailViewModel"
    }

}