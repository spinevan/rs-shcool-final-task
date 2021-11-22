package ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models.Asset

interface IAssetListener {
    fun loadNextPage()

}