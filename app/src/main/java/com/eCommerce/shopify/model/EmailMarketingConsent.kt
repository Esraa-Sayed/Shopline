package com.eCommerce.shopify.model

data class EmailMarketingConsent(
    val state: String,
    val opt_in_level: String,
    val consent_updated_at: Any? = null
)