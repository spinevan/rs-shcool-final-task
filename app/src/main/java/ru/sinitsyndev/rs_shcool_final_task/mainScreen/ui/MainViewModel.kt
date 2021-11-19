package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG
import javax.inject.Inject

class MainViewModel(private val getAssetsListUseCase: GetAssetsListUseCase): ViewModel() {

    //private val repo = CoinCapRepositoryImpl()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "!!!CoroutineExceptionHandler $exception")
    }

    init {

    }

    fun getAssets() {
        println("!!!!!!!!  INIT !!!!!!!!!")
        //var assets: AssetsList? = null
         viewModelScope.launch(errorHandler) {
           // val assets = repo.geAssets(0)
            // println(assets)
             val assets = getAssetsListUseCase.exec(1)
             //println(assets)
        }

    }

}

//class MainViewModelFactory(
//    private val getAssetsListUseCase: GetAssetsListUseCase
//    ): ViewModelProvider.Factory{
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return MainViewModel(getAssetsListUseCase) as T
//    }
//
//}

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