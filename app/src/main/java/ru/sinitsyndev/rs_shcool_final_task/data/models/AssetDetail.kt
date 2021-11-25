package ru.sinitsyndev.rs_shcool_final_task.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssetDetail(
    @Json(name = "data") val data: Asset
)