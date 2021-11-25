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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import ru.sinitsyndev.rs_shcool_final_task.MainActivity
import ru.sinitsyndev.rs_shcool_final_task.R
import ru.sinitsyndev.rs_shcool_final_task.appComponent
import ru.sinitsyndev.rs_shcool_final_task.databinding.FragmentAssetDetailsBinding
import ru.sinitsyndev.rs_shcool_final_task.databinding.FragmentMainBinding
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
        println(id)

        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myContext = activity as MainActivity
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

        lifecycleScope.launchWhenStarted {
            viewModel.assetDetails.collect { value ->
                //Log.d(LOG_TAG, "$value")
                with(binding) {
                    rankd.text = value.rank
                    symbold.text = value.symbol
                    named.text = value.name
                    supplyd.text = value.supply
                    maxSupplyd.text = value.maxSupply
                    marketCapUsdd.text = value.marketCapUsd
                    volumeUsd24Hrd.text = value.volumeUsd24Hr
                    priceUsdd.text = value.price
                    changePercent24Hrd.text = value.changePercent24Hr
                    vwap24Hrd.text = value.vwap24Hr

                    binding.topAppBar.title = value.name
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            myContext?.onBackPressed()
        }
        //binding.topAppBar.title = "CoCOC"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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