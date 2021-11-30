package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui

import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.AssetDetailsDecorator
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistory

sealed class AssetDetailScreenViewState{
    object Loading: AssetDetailScreenViewState()
    data class Error(
        val errorMessage: String
        ) :AssetDetailScreenViewState()
    data class AssetsDetails(
        val asset: AssetDetailsDecorator
        ) :AssetDetailScreenViewState()
    data class AssetsPriceHistory(
        val history: List<AssetPriceHistory>
    ) :AssetDetailScreenViewState()
}
