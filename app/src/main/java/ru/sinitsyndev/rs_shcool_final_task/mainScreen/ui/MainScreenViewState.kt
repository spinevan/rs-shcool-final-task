package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator

sealed class MainScreenViewState {
    object Loading: MainScreenViewState()

    data class Error(
        val errorMessage: String
    ) : MainScreenViewState()

    data class AssetsList(
        val assets: List<AssetDecorator>
    ) : MainScreenViewState()
}
