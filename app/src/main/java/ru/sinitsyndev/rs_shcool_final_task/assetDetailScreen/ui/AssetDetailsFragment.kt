package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.concurrent.TimeUnit
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IValueFormatter
import ru.sinitsyndev.rs_shcool_final_task.data.models.AssetPriceHistory
import ru.sinitsyndev.rs_shcool_final_task.utils.XAxisTimeFormatter
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList


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

    @RequiresApi(Build.VERSION_CODES.O)
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
                        is AssetDetailScreenViewState.AssetsPriceHistory -> {
                            setDataChart(uiState.history)
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

        initChart()
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

    private fun initChart() {
        val xAxis = binding.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE;
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.setCenterAxisLabels(true)
        xAxis.valueFormatter = XAxisTimeFormatter()

        xAxis.granularity = 24f
        xAxis.labelRotationAngle = -45f

        binding.chart.description.isEnabled = false
        binding.chart.axisRight.isEnabled = false
        binding.chart.setPadding(10, 100, 10, 10)
        context?.let {
            binding.chart.axisLeft.textColor = it.getColor(R.color.teal_700)
            binding.chart.xAxis.textColor = it.getColor(R.color.teal_700)
            binding.chart.legend.textColor = it.getColor(R.color.teal_700)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataChart(values: List<AssetPriceHistory>) {
        val listValues = mutableListOf<Entry>()
        values.map { item ->
            //listValues.add(Entry(item.time.toFloat(), item.priceUsd ))
            listValues.add(Entry(Instant.parse(item.date).toEpochMilli().toFloat(), item.priceUsd ))
        }

        val set1 = LineDataSet(listValues, "Price")
        set1.lineWidth = 2f
        set1.circleRadius = 4f
        set1.valueTextSize = 12f

        set1.setDrawCircleHole(false)
        set1.setDrawValues(false)

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1)

        val data = LineData(dataSets)
        binding.chart.data = data
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