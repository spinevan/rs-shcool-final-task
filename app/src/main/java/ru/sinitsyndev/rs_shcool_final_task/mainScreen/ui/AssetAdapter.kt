package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.sinitsyndev.rs_shcool_final_task.databinding.AssetItemViewBinding
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.AssetDecorator

class AssetAdapter(private val listener: IAssetListener) :
    ListAdapter<AssetDecorator, AssetViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AssetItemViewBinding.inflate(layoutInflater, parent, false)
        return AssetViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val current: AssetDecorator = getItem(position)
        holder.bind(current)

        if ((position >= itemCount - 2)) {
            listener.loadNextPage()
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<AssetDecorator>() {

            override fun areItemsTheSame(oldItem: AssetDecorator, newItem: AssetDecorator): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AssetDecorator, newItem: AssetDecorator): Boolean {
                return oldItem.id == newItem.id
            }

            override fun getChangePayload(oldItem: AssetDecorator, newItem: AssetDecorator) = Any()
        }
    }
}