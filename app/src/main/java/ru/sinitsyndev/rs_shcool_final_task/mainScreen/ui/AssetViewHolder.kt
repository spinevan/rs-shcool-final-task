package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import ru.sinitsyndev.rs_shcool_final_task.databinding.AssetItemViewBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset

class AssetViewHolder(
    private val binding: AssetItemViewBinding,
    private val listener: IAssetListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asset: Asset) {

        with(binding) {
            name.text = asset.name
            symbol.text = asset.symbol
            marketCapUsd.text = asset.marketCapUsd.toString()
            volumeUsd24Hr.text = asset.marketCapUsd.toString()
        }

    }
}