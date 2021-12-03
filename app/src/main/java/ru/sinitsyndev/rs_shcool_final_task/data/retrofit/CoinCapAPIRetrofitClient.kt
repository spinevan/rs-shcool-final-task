package ru.sinitsyndev.rs_shcool_final_task.data.retrofit

import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetDetail
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistoryData
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetsList
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_ITEMS_PER_PAGE
import javax.inject.Inject

class CoinCapAPIRetrofitClient @Inject constructor(
    private val coinCapAPI: CoinCapAPI
) {

    suspend fun getAssets(page: Int): AssetsList =
        coinCapAPI.getAssets(page * COIN_CAP_API_ITEMS_PER_PAGE)

    suspend fun getAsset(id: String): AssetDetail =
        coinCapAPI.getAsset(id)

    suspend fun getAssetPriceHistory(id: String, start: Long, end: Long): AssetPriceHistoryData =
        coinCapAPI.getAssetPriceHistory(id, "d1", start, end)
}
