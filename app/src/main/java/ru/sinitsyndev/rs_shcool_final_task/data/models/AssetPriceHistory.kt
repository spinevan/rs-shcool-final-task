package ru.sinitsyndev.rs_shcool_final_task.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.security.Timestamp
import java.util.*

@JsonClass(generateAdapter = true)
data class AssetPriceHistory(
    @Json(name = "priceUsd") val priceUsd: Float,
    @Json(name = "time") val time: Long,
    @Json(name = "date") val date: String
)
