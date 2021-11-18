package ru.sinitsyndev.rs_shcool_final_task.mainScreen.data

import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.AssetsList
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.retrofit.CoinCapAPIRetrofitClient

class CoinCapRepositoryImpl: ICoinCapRepository {

    private val client = CoinCapAPIRetrofitClient()

    override suspend fun geAssets(page: Int): List<Asset> {
        return client.getAssets(page).data
    }

}