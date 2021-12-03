package ru.sinitsyndev.rs_shcool_final_task.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetDetail
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistoryData
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetsList
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_ITEMS_PER_PAGE
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_KEY

interface CoinCapAPI {
//    @Headers("Accept-Encoding: deflate", "Authorization: Bearer $COIN_CAP_API_KEY")
    @Headers(value = ["Accept-Encoding: deflate", "Authorization: Bearer $COIN_CAP_API_KEY"])
    @GET("/v2/assets?limit=$COIN_CAP_API_ITEMS_PER_PAGE")
    suspend fun getAssets(@Query("offset") offset: Int): AssetsList

    @Headers(value = ["Accept-Encoding: deflate", "Authorization: Bearer $COIN_CAP_API_KEY"])
    @GET("/v2/assets/{id}")
    suspend fun getAsset(@Path("id") id: String): AssetDetail

    @Headers(value = ["Accept-Encoding: deflate", "Authorization: Bearer $COIN_CAP_API_KEY"])
    @GET("/v2/assets/{id}/history")
    suspend fun getAssetPriceHistory(
        @Path("id") id: String,
        @Query("interval") interval: String,
        @Query("start") start: Long,
        @Query("end") end: Long,
    ): AssetPriceHistoryData
}
