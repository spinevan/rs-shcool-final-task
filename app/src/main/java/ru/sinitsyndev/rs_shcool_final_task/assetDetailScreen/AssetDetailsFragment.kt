package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.sinitsyndev.rs_shcool_final_task.R


class AssetDetailsFragment : Fragment() {

    private var id: String? = null

    override fun onAttach(context: Context) {
        arguments?.let {
            id = it.getString(ASSET_ID)
        }
        println(id)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asset_details, container, false)
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