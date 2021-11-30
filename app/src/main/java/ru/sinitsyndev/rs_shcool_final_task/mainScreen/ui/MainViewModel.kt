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

    private var assetsPage = START_ASSETS_PAGE
    private var errorLoading = false

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "!!!CoroutineExceptionHandler $exception")
        errorLoading = true
        _viewState.value = MainScreenViewState.Error("$exception")
    }


    private val assets: MutableList<AssetDecorator> = mutableListOf()

    private val _viewState: MutableStateFlow<MainScreenViewState> = MutableStateFlow(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> get() = _viewState.asStateFlow()

    init {
        loadAssets()
    }



    fun loadNextAssetsPage() {
        Log.d(LOG_TAG, "loadNextAssetsPage $assetsPage")
        if (errorLoading) {
            return
        }

        assetsPage++
        loadAssets()
    }

    fun resetPage() {
        assetsPage = START_ASSETS_PAGE
        Log.d(LOG_TAG, "resetPage $assetsPage")
        assets.clear()
        loadAssets()
    }

    private fun loadAssets() {
        Log.d(LOG_TAG, "loadAssets $assetsPage")
        _viewState.value = MainScreenViewState.Loading

        viewModelScope.launch(errorHandler) {
            val newAssets = withContext(Dispatchers.IO){
                return@withContext getAssetsListUseCase.exec(assetsPage)
            }
            assets.addAll(newAssets)
            _viewState.value = MainScreenViewState.AssetsList(assets.toList())
            errorLoading = false
        }
    }

    fun reloadOnError() {
        Log.d(LOG_TAG, "reloadOnError $assetsPage")
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