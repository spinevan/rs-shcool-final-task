package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain

import android.content.SharedPreferences
import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistory
import ru.sinitsyndev.rs_shcool_final_task.utils.PRICE_HISTORY_DAYS_DEFAULT
import java.util.Calendar
import javax.inject.Inject

class GetAssetPriceHistoryUseCase @Inject constructor(
    private val repository: CoinCapRepositoryImpl,
    private val prefs: SharedPreferences
): IGetAssetPriceHistoryUseCase {
    override suspend fun getHistory(id: String): List<AssetPriceHistory> {
        val calendar = Calendar.getInstance()
        val to = calendar.timeInMillis
        val from = to - 60 * 60 * 24 * 1000 * prefs.getInt("price_history_days", PRICE_HISTORY_DAYS_DEFAULT)
        return repository.geAssetPriceHistory(id, from, to)
    }
}
