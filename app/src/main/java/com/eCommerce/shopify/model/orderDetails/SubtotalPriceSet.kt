package com.eCommerce.shopify.model.orderDetails

data class SubtotalPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)