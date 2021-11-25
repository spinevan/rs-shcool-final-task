package ru.sinitsyndev.rs_shcool_final_task.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Asset(
    @Json(name = "id") val id: String,
    @Json(name = "rank") val rank: Int,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String,
    @Json(name = "supply") val supply: Float,
    @Json(name = "marketCapUsd") val marketCapUsd: Float,
    @Json(name = "volumeUsd24Hr") val volumeUsd24Hr: Float,
    @Json(name = "priceUsd") val priceUsd: Float,
    @Json(name = "changePercent24Hr") val changePercent24Hr: Float,
    @Json(name = "maxSupply") val maxSupply: Float?,
    @Json(name = "vwap24Hr") val vwap24Hr: Float?
)
