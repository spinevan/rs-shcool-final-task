package ru.sinitsyndev.rs_shcool_final_task.mainScreen.data

import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.AssetsList

interface ICoinCapRepository {
    suspend fun geAssets(page: Int): List<Asset>
}