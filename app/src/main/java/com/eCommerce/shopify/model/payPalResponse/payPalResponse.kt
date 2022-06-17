package com.eCommerce.shopify.model.payPalResponse

data class payPalResponse(
    val client: Client,
    val response: Response,
    val response_type: String
)