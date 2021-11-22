package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.sinitsyndev.rs_shcool_final_task.databinding.AssetItemViewBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset

class AssetAdapter(private val listener: IAssetListener) :
    ListAdapter<Asset, AssetViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AssetItemViewBinding.inflate(layoutInflater, parent, false)
        return AssetViewHolder(binding, listener, binding.root.context.resources)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val current: Asset = getItem(position)
        holder.bind(current)

        if ((position >= itemCount - 1)) {
            listener.loadNextPage()
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Asset>() {

            override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
                return oldItem.id == newItem.id
            }

            override fun getChangePayload(oldItem: Asset, newItem: Asset) = Any()
        }
    }
}