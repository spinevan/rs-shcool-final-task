package ru.sinitsyndev.rs_shcool_final_task.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.AssetsList
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG

class MainViewModel: ViewModel() {

    private val repo = CoinCapRepositoryImpl()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "!!!CoroutineExceptionHandler $exception")
    }

    init {

    }

    fun getAssets() {
        println("!!!!!!!!  INIT !!!!!!!!!")
        //var assets: AssetsList? = null
         viewModelScope.launch(errorHandler) {
            val assets = repo.geAssets(0)
             println(assets)
        }

    }

}