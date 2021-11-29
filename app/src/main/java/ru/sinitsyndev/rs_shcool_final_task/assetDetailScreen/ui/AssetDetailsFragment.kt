package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.sinitsyndev.rs_shcool_final_task.MainActivity
import ru.sinitsyndev.rs_shcool_final_task.R
import ru.sinitsyndev.rs_shcool_final_task.appComponent
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.AssetDetailsDecorator
import ru.sinitsyndev.rs_shcool_final_task.databinding.FragmentAssetDetailsBinding
import ru.sinitsyndev.rs_shcool_final_task.databinding.FragmentMainBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui.MainScreenViewState
import ru.sinitsyndev.rs_shcool_final_task.utils.LOG_TAG
import javax.inject.Inject


class AssetDetailsFragment : Fragment() {

    private var id: String = ""
    private var _binding: FragmentAssetDetailsBinding? = null
    private val binding get() = _binding!!

    private var myContext: MainActivity? = null

    private val viewModel: AssetDetailsViewModel by viewModels {
        factory.create(id)
    }
    @Inject
    lateinit var factory: AssetDetailsViewModelFactory.Factory

    override fun onAttach(context: Context) {
        arguments?.let {
            id = it.getString(ASSET_ID).toString()
        }

        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myContext = activity as MainActivity

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { uiState ->
                    when (uiState) {
                        is AssetDetailScreenViewState.Loading -> {
                            showLoading()
                        }
                        is AssetDetailScreenViewState.Error -> {
                            showError(uiState.errorMessage)
                        }
                        is AssetDetailScreenViewState.AssetsDetails -> {
                            showAssetsDetail(uiState.asset)
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
        _binding = FragmentAssetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setNavigationOnClickListener {
            myContext?.onBackPressed()
        }
        binding.retryBtn.setOnClickListener {
            viewModel.reload()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        Log.d(LOG_TAG, "showLoading")
        binding.frameLoading.isVisible = true
        binding.scrollDetails.isVisible = false
        binding.frameError.isVisible = false
    }

    private fun showError(error: String) {
        Log.d(LOG_TAG, "showError $error")
        binding.frameLoading.isVisible = false
        binding.scrollDetails.isVisible = false
        binding.frameError.isVisible = true
    }

    private fun showAssetsDetail(asset: AssetDetailsDecorator) {
        with(binding) {
            rankd.text = asset.rank
            symbold.text = asset.symbol
            named.text = asset.name
            supplyd.text = asset.supply
            maxSupplyd.text = asset.maxSupply
            marketCapUsdd.text = asset.marketCapUsd
            volumeUsd24Hrd.text = asset.volumeUsd24Hr
            priceUsdd.text = asset.price
            changePercent24Hrd.text = asset.changePercent24Hr
            vwap24Hrd.text = asset.vwap24Hr
            scrollDetails.isVisible = true
            frameLoading.isVisible = false
            binding.frameError.isVisible = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            AssetDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ASSET_ID, id)
                }
            }

        const val ASSET_ID = "ASSET_ID"
    }
}