package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import androidx.recyclerview.widget.RecyclerView
import ru.sinitsyndev.rs_shcool_final_task.databinding.AssetItemViewBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator

class AssetViewHolder(
    private val binding: AssetItemViewBinding,
    private val listener: IAssetListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asset: AssetDecorator) {

        with(binding) {
            name.text = asset.name
            symbol.text = asset.symbol
            marketCapUsd.text = asset.marketCapUsd
            volumeUsd24Hr.text = asset.volumeUsd24Hr
            rank.text = asset.rank

            root.setOnClickListener {
                listener.openAssetDetail(asset.id)
            }
        }

    }
}