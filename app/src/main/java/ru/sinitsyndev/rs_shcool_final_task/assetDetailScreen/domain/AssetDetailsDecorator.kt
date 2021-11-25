package ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain

import ru.sinitsyndev.rs_shcool_final_task.data.models.Asset

data class AssetDetailsDecorator(private val asset: Asset) {
    var id: String = ""
    var name: String = ""
    var symbol: String = ""
    var marketCapUsd: String = ""
    var volumeUsd24Hr: String = ""
    var rank: String = ""
    var price: String = ""
    var supply: String = ""
    var maxSupply: String = ""
    var changePercent24Hr: String = ""
    var vwap24Hr: String = ""

    init {
        name = asset.name
        symbol = asset.symbol
        marketCapUsd = String.format("%.6f", asset.marketCapUsd)+"$"
        volumeUsd24Hr = String.format("%.6f", asset.volumeUsd24Hr)+"$"
        rank = asset.rank.toString()
        price = String.format("%.6f", asset.priceUsd)+"$"
        supply = String.format("%.6f", asset.supply)

        if (asset.maxSupply == null) {
            maxSupply = "-"
        }else {
            maxSupply = String.format("%.6f", asset.maxSupply)
        }
        changePercent24Hr = String.format("%.6f", asset.changePercent24Hr)

        if (asset.maxSupply == null) {
            vwap24Hr = "-"
        } else {
            vwap24Hr = String.format("%.6f", asset.vwap24Hr)
        }
    }

}
