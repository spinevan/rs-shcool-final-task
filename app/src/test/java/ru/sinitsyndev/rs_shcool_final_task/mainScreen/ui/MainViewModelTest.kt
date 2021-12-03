package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.sinitsyndev.rs_shcool_final_task.MainCoroutineRule
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase

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