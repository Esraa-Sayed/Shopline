package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteAttribute(
    val name: String,
    val value: String
): Parcelable