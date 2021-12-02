package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.sinitsyndev.rs_shcool_final_task.MainCoroutineRule
import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPI
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPIRetrofitClient
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_BASE_URL


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class )
class MainViewModelTest : TestCase() {

    private val getAssetsListUseCase: GetAssetsListUseCase = mock(GetAssetsListUseCase::class.java)

    // WITH
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `should load data on init`() = mainCoroutineRule.runBlockingTest {
        MainViewModel(getAssetsListUseCase)
        verify(getAssetsListUseCase, atLeastOnce()).exec(0)
    }

    @Test
    fun `should load data on reset page`() = mainCoroutineRule.runBlockingTest {
        val viewModel = MainViewModel(getAssetsListUseCase)
        clearInvocations(getAssetsListUseCase)
        viewModel.resetPage()
        verify(getAssetsListUseCase, atLeastOnce() ).exec(0)
    }

    @Test
    fun `should load data for next page`() = mainCoroutineRule.runBlockingTest {
        val viewModel = MainViewModel(getAssetsListUseCase)
        clearInvocations(getAssetsListUseCase)
        viewModel.loadNextAssetsPage()
        verify(getAssetsListUseCase, atLeastOnce()).exec(1)
    }
}