package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopMoney(
    val amount: String,
    val currency_code: String
):Parcelable