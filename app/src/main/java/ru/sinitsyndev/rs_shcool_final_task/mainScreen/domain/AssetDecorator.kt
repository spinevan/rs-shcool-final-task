package ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain

import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset

data class AssetDecorator(private val asset: Asset) {

    var id: String = ""
    var name: String = ""
    var symbol: String = ""
    var marketCapUsd: String = ""
    var volumeUsd24Hr: String = ""
    var rank: String = ""
    var price: String = ""

    init {
        id = asset.id
        name = asset.name
        symbol = asset.symbol
        marketCapUsd = String.format("%.0f", asset.marketCapUsd) + "$"
        volumeUsd24Hr = String.format("%.0f", asset.volumeUsd24Hr) + "$"
        rank = asset.rank.toString()
        price = String.format("%.6f", asset.priceUsd) + "$"
    }
}
