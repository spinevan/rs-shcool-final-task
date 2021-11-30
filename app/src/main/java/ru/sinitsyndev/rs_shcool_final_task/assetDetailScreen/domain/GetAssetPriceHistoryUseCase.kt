package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain

import android.content.Context
import androidx.preference.PreferenceManager
import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistory
import java.util.*
import javax.inject.Inject

class GetAssetPriceHistoryUseCase @Inject constructor(
    private val repository: CoinCapRepositoryImpl,
    private val context: Context
) {
    suspend fun getHistory(id: String): List<AssetPriceHistory> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val calendar = Calendar.getInstance()
        val to = calendar.timeInMillis
        val from = to - 60*60*24*1000 * prefs.getInt("price_history_days", 3);
        return repository.geAssetPriceHistory(id, from, to)
    }
}