package com.eCommerce.shopify.model.orderDetails

data class TotalShippingPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)