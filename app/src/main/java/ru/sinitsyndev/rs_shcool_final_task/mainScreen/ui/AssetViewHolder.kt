package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import ru.sinitsyndev.rs_shcool_final_task.databinding.AssetItemViewBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset

class AssetViewHolder(
    private val binding: AssetItemViewBinding,
    private val listener: IAssetListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asset: Asset) {

        with(binding) {
            name.text = asset.name
            symbol.text = asset.symbol
            marketCapUsd.text = String.format("%.0f", asset.marketCapUsd)
            volumeUsd24Hr.text = String.format("%.0f", asset.volumeUsd24Hr)
            rank.text = asset.rank.toString()
        }

    }
}