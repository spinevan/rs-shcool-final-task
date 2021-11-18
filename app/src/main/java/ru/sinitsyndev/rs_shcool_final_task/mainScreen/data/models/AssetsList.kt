package ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssetsList(
    @Json(name = "data") val data: List<Asset>
)