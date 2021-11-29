package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.sinitsyndev.rs_shcool_final_task.R
import ru.sinitsyndev.rs_shcool_final_task.appComponent
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui.AssetDetailsFragment
import ru.sinitsyndev.rs_shcool_final_task.databinding.FragmentMainBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator
import ru.sinitsyndev.rs_shcool_final_task.utils.COUNT_COLUMNS_ASSET_RECYCLER
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG
import javax.inject.Inject

class MainFragment : Fragment(), IAssetListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val assetsAdapter = AssetAdapter(this)

    private val viewModel: MainViewModel by viewModels{
        factory.create()
    }
    @Inject
    lateinit var factory: MainViewModelFactory.Factory


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { uiState ->
                    when (uiState) {
                        is MainScreenViewState.Loading -> {
                            showLoading()
                        }
                        is MainScreenViewState.Error -> {
                            showError(uiState.errorMessage)
                        }
                        is MainScreenViewState.AssetsList -> {
                            showAssetsList(uiState.assets)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            assetsRecycler.apply {
                layoutManager = GridLayoutManager(context, COUNT_COLUMNS_ASSET_RECYCLER)
                adapter = assetsAdapter
            }

            reloadBtn.setOnClickListener {
                viewModel.reloadOnError()
            }

            swiperefresh.setOnRefreshListener {
                viewModel.resetPage()
            }
            topAppBar.setOnMenuItemClickListener {  menuItem ->
                when (menuItem.itemId) {
                    R.id.preferences -> {
                        view.findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
                        true
                    }
                    else -> false
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadNextPage() {
        viewModel.loadNextAssetsPage()
    }

    override fun openAssetDetail(id: String) {
        view?.findNavController()?.navigate(R.id.action_mainFragment_to_assetDetailsFragment, bundleOf(
            AssetDetailsFragment.ASSET_ID to id))
    }

    private fun showLoading() {
        binding.swiperefresh.isRefreshing = true
        binding.reloadBtn.isVisible = false
    }

    private fun showError(errorMessage: String) {
        binding.swiperefresh.isRefreshing = false
        Log.d(LOG_TAG, errorMessage)
        binding.reloadBtn.isVisible = true
    }

    private fun showAssetsList(assets: List<AssetDecorator>) {
        binding.swiperefresh.isRefreshing = false
        binding.reloadBtn.isVisible = false
        assetsAdapter.submitList(assets)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}