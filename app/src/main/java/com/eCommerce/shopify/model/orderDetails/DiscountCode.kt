package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscountCode(
    val amount: String? = null,
    val code: String? = null,
    val type: String? = null
): Parcelable