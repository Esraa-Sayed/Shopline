package com.eCommerce.shopify.model

import java.io.Serializable

data class Customer(
    var accepts_marketing: Boolean = false,
    var accepts_marketing_updated_at: String = "",
    var addresses: List<Addresse> = emptyList(),
    var admin_graphql_api_id: String? = null,
    var created_at: String? = null,
    var currency: String? = null,
    var default_address: Addresse? = null,
    var email: String? = null,
    //var email_marketing_consent: EmailMarketingConsent,
    var first_name: String? = null,
    var id: Long? = null,
    var last_name: String? = null,
    var last_order_id: Any? = null,
    var last_order_name: Any? = null,
    var marketing_opt_in_level: Any? = null,
    var multipass_identifier: Any? = null,
    var note: Any? = null,
    var orders_count: Int? = null,
    var phone: Any? = null,
    var sms_marketing_consent: Any? = null,
    var state: String? = null,
    var tags: String? = null,
    var tax_exempt: Boolean? = null,
    var tax_exemptions: List<Any>? = null,
    var total_spent: String? = null,
    var updated_at: String? = null,
    var verified_email: Boolean? = null
): Serializable
