package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaxLine(
    val price: String,
    val price_set: PriceSet,
    val rate: Double,
    val title: String
):Parcelable