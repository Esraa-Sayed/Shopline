package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentDetails(
    //val avs_result_code: Any,
    //val credit_card_bin: Any,
    val credit_card_company: String,
    //val credit_card_expiration_month: Any,
    //val credit_card_expiration_year: Any,
    //val credit_card_name: Any,
    val credit_card_number: String,
//    val credit_card_wallet: Any,
//    val cvv_result_code: Any
): Parcelable