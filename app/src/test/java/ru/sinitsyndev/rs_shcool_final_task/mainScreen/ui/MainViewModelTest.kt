package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
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
import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPI
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPIRetrofitClient
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_BASE_URL


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class )
class MainViewModelTest : TestCase() {

    //@Rule
    //public val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val getAssetsListUseCase: GetAssetsListUseCase = mock(GetAssetsListUseCase::class.java)

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)


        viewModel = MainViewModel(getAssetsListUseCase)

    }

    @After
    public override fun tearDown() {
        // Resets state of the [Dispatchers.Main] to the original main dispatcher.
        // For example, in Android Main thread dispatcher will be set as [Dispatchers.Main].
        Dispatchers.resetMain()

        // Clean up the TestCoroutineDispatcher to make sure no other work is running.
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should load data on init`() = runBlockingTest {
        verify(getAssetsListUseCase).exec(0)
    }

    @Test
    fun `should load data on reset page`() = runBlockingTest {
        viewModel.resetPage()
        verify(getAssetsListUseCase, times(2)).exec(0)
    }

    @Test
    fun `should load data for next page`() = runBlockingTest {
        viewModel.loadNextAssetsPage()
        verify(getAssetsListUseCase, times(1)).exec(0)
    }

//    @Test
//    fun `should view state is loading on load data`() = runBlockingTest {
//        viewModel.loadNextAssetsPage()
//        val firstState = viewModel.viewState.first()
//        //println(firstState)
//        Assert.assertEquals(firstState, MainScreenViewState.Loading)
//    }

//    @Test
//    fun `should view state is Assets on init`() = runBlockingTest {
//        //val secondState = viewModel.viewState.take(2).toList()
//        //println(secondState)
//        //Assert.assertEquals(secondState, MainScreenViewState.Loading)
//
//        val result = arrayListOf<MainScreenViewState>()
//        val job = launch {
//            viewModel.viewState.toList(result) //now it should work
//        }
//        //assertThat(viewModel.viewState.value is MainScreenViewState.AssetsList).isTrue()
//        println(result.toString())
//
//        job.cancel()
//    }

}