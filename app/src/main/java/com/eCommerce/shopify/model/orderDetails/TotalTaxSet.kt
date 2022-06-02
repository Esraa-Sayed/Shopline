package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TotalTaxSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
): Parcelable