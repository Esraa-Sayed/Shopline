package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscountCode(
    val amount: String,
    val code: String,
    val type: String
): Parcelable