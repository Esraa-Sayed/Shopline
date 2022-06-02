package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscountApplication(
    val allocation_method: String,
    val code: String,
    val target_selection: String,
    val target_type: String,
    val type: String,
    val value: String,
    val value_type: String
): Parcelable