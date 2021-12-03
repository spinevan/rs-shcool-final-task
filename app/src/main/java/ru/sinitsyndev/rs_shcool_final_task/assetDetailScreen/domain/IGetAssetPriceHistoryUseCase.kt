package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain

import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistory

interface IGetAssetPriceHistoryUseCase {
    suspend fun getHistory(id: String): List<AssetPriceHistory>
}