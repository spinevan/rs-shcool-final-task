package ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.AssetsList
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_BASE_URL
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_ITEMS_PER_PAGE

class CoinCapAPIRetrofitClient {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(COIN_CAP_API_BASE_URL)
        .build()

    private val coinCapAPIService = retrofit.create(CoinCapAPI::class.java)

    suspend fun getAssets(page: Int): AssetsList =
        coinCapAPIService.getAssets(page * COIN_CAP_API_ITEMS_PER_PAGE)
}