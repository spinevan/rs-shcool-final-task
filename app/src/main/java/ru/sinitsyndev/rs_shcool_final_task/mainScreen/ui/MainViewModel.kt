package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG
import ru.sinitsyndev.rs_shcool_final_task.utils.START_ASSETS_PAGE
import javax.inject.Inject

class MainViewModel(private val getAssetsListUseCase: GetAssetsListUseCase): ViewModel() {

    //private val repo = CoinCapRepositoryImpl()

    private var assetsPage = START_ASSETS_PAGE

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "!!!CoroutineExceptionHandler $exception")
        _errorLoading.value = true
        _loading.value = false
    }

    private val _assetsList = MutableSharedFlow<List<AssetDecorator>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val assetsList: SharedFlow<List<AssetDecorator>> = _assetsList.asSharedFlow()

    private val assets: MutableList<AssetDecorator> = mutableListOf()

    private val _errorLoading = MutableStateFlow(false)
    val errorLoading: StateFlow<Boolean> get() = _errorLoading.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading.asStateFlow()

    init {
        viewModelScope.launch(errorHandler) {
            _loading.value = true
            val initAssets = withContext(Dispatchers.IO){
                return@withContext getAssetsListUseCase.exec(assetsPage)
            }
            assets.addAll(initAssets)
           _assetsList.emitAll(
               flow {
                   emit(assets)
               }
           )
            _errorLoading.value = false
            _loading.value = false
        }
    }



    fun loadNextAssetsPage() {
        if (_errorLoading.value) {
           return
        }

        assetsPage++
        loadAssets()
    }

    fun resetPage() {
        assetsPage = START_ASSETS_PAGE
        assets.clear()
        loadAssets()
    }

    private fun loadAssets() {
        _loading.value = true
        _errorLoading.value = false

        viewModelScope.launch(errorHandler) {
            val newAssets = withContext(Dispatchers.IO){
                return@withContext getAssetsListUseCase.exec(assetsPage)
            }
            assets.addAll(newAssets)
            _assetsList.tryEmit(assets.toList())
            _loading.value = false
        }
    }

    fun reloadOnError() {
        loadAssets()
    }

}

class MainViewModelFactory(
    private val getAssetsListUseCase: GetAssetsListUseCase,
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getAssetsListUseCase) as T
    }

    class Factory @Inject constructor(private val getAssetsListUseCase: GetAssetsListUseCase) {
        fun create(): MainViewModelFactory{
            return MainViewModelFactory(getAssetsListUseCase)
        }
    }
}