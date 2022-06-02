package com.eCommerce.shopify.model.orderDetails

import com.eCommerce.shopify.model.Customer

data class Order(
    val admin_graphql_api_id: String,
    val app_id: Any,
    val billing_address: BillingAddress,
    val browser_ip: String,
    val buyer_accepts_marketing: Boolean,
    val cancel_reason: Any,
    val cancelled_at: Any,
    val cart_token: String,
    val checkout_id: Int,
    val checkout_token: String,
    val client_details: ClientDetails,
    val closed_at: Any,
    val confirmed: Boolean,
    val contact_email: String,
    val created_at: String,
    val currency: String,
    val current_subtotal_price: String,
    val current_subtotal_price_set: CurrentSubtotalPriceSet,
    val current_total_discounts: String,
    val current_total_discounts_set: CurrentTotalDiscountsSet,
    val current_total_duties_set: Any,
    val current_total_price: String,
    val current_total_price_set: CurrentTotalPriceSet,
    val current_total_tax: String,
    val current_total_tax_set: CurrentTotalTaxSet,
    val customer: Customer,
    val customer_locale: Any,
    val device_id: Any,
    val discount_applications: List<DiscountApplication>,
    val discount_codes: List<DiscountCode>,
    val email: String,
    val estimated_taxes: Boolean,
    val financial_status: String,
    val fulfillment_status: Any,
    val gateway: String,
    val id: Int,
    val landing_site: String,
    val landing_site_ref: String,
    val line_items: List<LineItem>,
    val location_id: Any,
    val name: String,
    val note: Any,
    val note_attributes: List<NoteAttribute>,
    val number: Int,
    val order_number: Int,
    val order_status_url: String,
    val original_total_duties_set: Any,
    val payment_details: PaymentDetails,
    val payment_gateway_names: List<String>,
    val phone: String,
    val presentment_currency: String,
    val processed_at: String,
    val processing_method: String,
    val reference: String,
    val referring_site: String,
    val shipping_address: ShippingAddress,
    val shipping_lines: List<ShippingLine>,
    val source_identifier: String,
    val source_name: String,
    val source_url: Any,
    val subtotal_price: String,
    val subtotal_price_set: SubtotalPriceSet,
    val tags: String,
    val tax_lines: List<TaxLine>,
    val taxes_included: Boolean,
    val test: Boolean,
    val token: String,
    val total_discounts: String,
    val total_discounts_set: TotalDiscountsSet,
    val total_line_items_price: String,
    val total_line_items_price_set: TotalLineItemsPriceSet,
    val total_outstanding: String,
    val total_price: String,
    val total_price_set: TotalPriceSet,
    val total_price_usd: String,
    val total_shipping_price_set: TotalShippingPriceSet,
    val total_tax: String,
    val total_tax_set: TotalTaxSet,
    val total_tip_received: String,
    val total_weight: Int,
    val updated_at: String,
    val user_id: Any
)