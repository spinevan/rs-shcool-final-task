package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import ru.sinitsyndev.rs_shcool_final_task.MainCoroutineRule
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetDetailsUseCase
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetPriceHistoryUseCase

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class )
class AssetDetailsViewModelTest : TestCase() {

    private val getAssetDetailsUseCase = mock(GetAssetDetailsUseCase::class.java)
    private val getAssetPriceHistoryUseCase = mock(GetAssetPriceHistoryUseCase::class.java)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `should load data on init`() = mainCoroutineRule.runBlockingTest {
        AssetDetailsViewModel(getAssetDetailsUseCase, getAssetPriceHistoryUseCase, "test")
        Mockito.verify(getAssetPriceHistoryUseCase, Mockito.atLeastOnce()).getHistory("test")
    }

    @Test
    fun `should load data on reload`() = mainCoroutineRule.runBlockingTest {
        val viewModel = AssetDetailsViewModel(getAssetDetailsUseCase, getAssetPriceHistoryUseCase, "test")
        Mockito.clearInvocations(getAssetPriceHistoryUseCase)
        viewModel.reload()
        Mockito.verify(getAssetPriceHistoryUseCase, Mockito.atLeastOnce()).getHistory("test")
    }
}