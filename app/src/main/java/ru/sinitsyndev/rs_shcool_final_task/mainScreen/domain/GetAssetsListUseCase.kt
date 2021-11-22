package ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
//        println("~~~getAssetsListUseCasegetAssetsListUseCasegetAssetsListUseCasegetAssetsListUseCase")
//        return withContext(Dispatchers.IO) {
//           repository.geAssets(page)
//        }
        val res = repository.geAssets(page)
        //println(res)
        return res
    //return repository.geAssets(page)

    }
}