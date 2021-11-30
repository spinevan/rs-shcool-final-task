package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain

import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset
import javax.inject.Inject

class GetAssetDetailsUseCase @Inject constructor(
    private val repository: CoinCapRepositoryImpl
) {

    suspend fun getDetails(id: String): AssetDetailsDecorator {

        return AssetDetailsDecorator(repository.geAsset(id))
    }

}