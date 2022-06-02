package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillingAddress(
    val address1: String,
    val address2: String,
    val city: String,
    //val company: Any,
    val country: String,
    val country_code: String,
    val first_name: String,
    val last_name: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone: String,
    val province: String,
    val province_code: String,
    val zip: String
): Parcelable