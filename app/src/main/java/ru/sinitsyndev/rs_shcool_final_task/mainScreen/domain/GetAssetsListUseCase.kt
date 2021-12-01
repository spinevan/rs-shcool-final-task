package ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain

import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import javax.inject.Inject

class GetAssetsListUseCase @Inject constructor(
    private val repository: CoinCapRepositoryImpl
) {

    suspend fun exec(page: Int): List<AssetDecorator> {

        val listAssets = repository.geAssets(page)
        val res: MutableList<AssetDecorator> = mutableListOf()
        listAssets.map { asset ->
            res.add(AssetDecorator(asset))
        }
        return res
    }
}
