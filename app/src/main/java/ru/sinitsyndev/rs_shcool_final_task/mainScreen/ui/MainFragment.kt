package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import ru.sinitsyndev.rs_shcool_final_task.R
import ru.sinitsyndev.rs_shcool_final_task.appComponent
import ru.sinitsyndev.rs_shcool_final_task.databinding.FragmentMainBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset
import ru.sinitsyndev.rs_shcool_final_task.utils.COUNT_COLUMNS_ASSET_RECYCLER
import javax.inject.Inject

class MainFragment : Fragment(), IAssetListener {

 //   private val viewModel: MainViewModel by viewModels()

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.assetsRecycler.apply {
            layoutManager = GridLayoutManager(context, COUNT_COLUMNS_ASSET_RECYCLER)
            adapter = assetsAdapter
        }

        binding.reloadBtn.setOnClickListener {
            viewModel.reloadOnError()
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.resetPage()
        }

//        binding.swiperefresh.isRefreshing

        lifecycleScope.launchWhenStarted {
            viewModel.assetsList.collect { items ->
                assetsAdapter.submitList(items)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorLoading.collect { value ->
                binding.reloadBtn.isVisible = value
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorLoading.collect { value ->
                binding.reloadBtn.isVisible = value
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loading.collect { value ->
                binding.swiperefresh.isRefreshing = value
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

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}