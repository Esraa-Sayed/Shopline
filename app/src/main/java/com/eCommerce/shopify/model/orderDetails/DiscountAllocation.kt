package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscountAllocation(
    val amount: String,
    val amount_set: AmountSet,
    val discount_application_index: Int
):Parcelable