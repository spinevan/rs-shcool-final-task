package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain

interface IGetAssetDetailsUseCase {
    suspend fun getDetails(id: String): AssetDetailsDecorator
}