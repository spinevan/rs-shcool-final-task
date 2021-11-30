package ru.sinitsyndev.rs_shcool_final_task.data

import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistory
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPIRetrofitClient
import javax.inject.Inject

class CoinCapRepositoryImpl @Inject constructor(
    private val client: CoinCapAPIRetrofitClient
) : ICoinCapRepository {

    override suspend fun geAssets(page: Int): List<Asset> {
        return client.getAssets(page).data
    }

    override suspend fun geAsset(id: String): Asset {
        return client.getAsset(id).data
    }

    override suspend fun geAssetPriceHistory(id: String, start: Long, end: Long): List<AssetPriceHistory> {
        return client.getAssetPriceHistory(id, start, end).data
    }

}