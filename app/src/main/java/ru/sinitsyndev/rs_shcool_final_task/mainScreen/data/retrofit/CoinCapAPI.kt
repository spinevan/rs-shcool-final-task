package ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.AssetsList
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_ITEMS_PER_PAGE

interface CoinCapAPI {
    @Headers("Accept-Encoding: deflate")
    @GET("/v2/assets?limit=$COIN_CAP_API_ITEMS_PER_PAGE")
    suspend fun getAssets(@Query("offset") offset: Int): AssetsList
}