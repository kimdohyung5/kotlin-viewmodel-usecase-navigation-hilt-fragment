package com.kimdo.flowsampleapp.domain.use_case.get_coin

import com.kimdo.flowsampleapp.common.Resource
import com.kimdo.flowsampleapp.domain.model.CoinDetail
import com.kimdo.flowsampleapp.domain.repository.CoinRepository
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository){

    operator fun invoke( coidId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit( Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coidId).toCoinDetail()
            emit( Resource.Success<CoinDetail>(coin))
        } catch( e: HttpException) {
            emit( Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch( e: IOException) {
            emit( Resource.Error<CoinDetail>("Couldn't  reach server. check your internet connection."))
        }
    }
}