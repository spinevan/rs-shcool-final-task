package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.AssetDetailsDecorator
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetDetailsUseCase
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetPriceHistoryUseCase
import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui.MainScreenViewState
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG


class AssetDetailsViewModel(
    private val getAssetDetailsUseCase: GetAssetDetailsUseCase,
    private val getAssetPriceHistoryUseCase: GetAssetPriceHistoryUseCase,
    val id: String
): ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "!!!getAssetDetailsUseCase CoroutineExceptionHandler $exception")
        _viewState.value = AssetDetailScreenViewState.Error("$exception")
    }

//    private val _assetDetails = MutableSharedFlow<AssetDetailsDecorator>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
//    val assetDetails: SharedFlow<AssetDetailsDecorator> = _assetDetails.asSharedFlow()

    private val _viewState: MutableStateFlow<AssetDetailScreenViewState> = MutableStateFlow(
        AssetDetailScreenViewState.Loading)
    val viewState: StateFlow<AssetDetailScreenViewState> get() = _viewState.asStateFlow()

    init {
        loadDetails()
    }

    fun reload() {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch(errorHandler) {
            _viewState.value = AssetDetailScreenViewState.Loading

            val priseHistory = withContext(Dispatchers.IO) {
                return@withContext getAssetPriceHistoryUseCase.getHistory(id)
            }
            _viewState.value = AssetDetailScreenViewState.AssetsPriceHistory(priseHistory)

            val initAssetDetails = withContext(Dispatchers.IO) {
                return@withContext getAssetDetailsUseCase.getDetails(id)
            }
            _viewState.value = AssetDetailScreenViewState.AssetsDetails(initAssetDetails)
        }
    }
}

class AssetDetailsViewModelFactory @AssistedInject constructor(
    private val getAssetDetailsUseCase: GetAssetDetailsUseCase,
    private val getAssetPriceHistoryUseCase: GetAssetPriceHistoryUseCase,
    @Assisted("id") private val id: String
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //require(modelClass == QuizViewModel::class)
        return AssetDetailsViewModel(getAssetDetailsUseCase, getAssetPriceHistoryUseCase, id) as T
    }

    @AssistedFactory
    interface Factory  {

        fun create(@Assisted("id") id: String): AssetDetailsViewModelFactory
    }
}
