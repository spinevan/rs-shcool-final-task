package ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain

interface IGetAssetsListUseCase {
    suspend fun exec(page: Int): List<AssetDecorator>
}