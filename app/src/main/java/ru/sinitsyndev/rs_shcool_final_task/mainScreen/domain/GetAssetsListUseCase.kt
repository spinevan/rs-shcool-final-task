package ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain

import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.ICoinCapRepository
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import javax.inject.Inject

//class GetAssetsListUseCase @Inject constructor(private val repository: CoinCapRepositoryImpl) {
//    suspend fun exec(page: Int): List<Asset> {
//        return repository.geAssets(page)
//    }
//}

class GetAssetsListUseCase @Inject constructor(private val repository: CoinCapRepositoryImpl) {
    suspend fun exec(page: Int): List<Asset> {
        println("~~~getAssetsListUseCasegetAssetsListUseCasegetAssetsListUseCasegetAssetsListUseCase")
        return repository.geAssets(page)

    }
}