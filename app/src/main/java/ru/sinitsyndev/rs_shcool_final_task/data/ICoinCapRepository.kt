package ru.sinitsyndev.rs_shcool_final_task.data

import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset

interface ICoinCapRepository {
    suspend fun geAssets(page: Int): List<Asset>
    suspend fun geAsset(id: String): Asset
}