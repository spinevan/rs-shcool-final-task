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
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.AssetDetailsDecorator
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetDetailsUseCase
import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG


class AssetDetailsViewModel(
    private val getAssetDetailsUseCase: GetAssetDetailsUseCase,
    id: String
): ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "!!!222 CoroutineExceptionHandler $exception")
    }

    private val _assetDetails = MutableSharedFlow<AssetDetailsDecorator>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val assetDetails: SharedFlow<AssetDetailsDecorator> = _assetDetails.asSharedFlow()

    init {
        viewModelScope.launch(errorHandler) {
            val initAssetDetails = withContext(Dispatchers.IO) {
                return@withContext getAssetDetailsUseCase.getDetails(id)
            }
            _assetDetails.emitAll(
                flow {
                    emit(initAssetDetails)
                }
            )
        }
    }


}

class AssetDetailsViewModelFactory @AssistedInject constructor(
    private val getAssetDetailsUseCase: GetAssetDetailsUseCase,
    @Assisted("id") private val id: String
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //require(modelClass == QuizViewModel::class)
        return AssetDetailsViewModel(getAssetDetailsUseCase, id) as T
    }

    @AssistedFactory
    interface Factory  {

        fun create(@Assisted("id") id: String): AssetDetailsViewModelFactory
    }
}
