package com.eCommerce.shopify.model.orderDetails

data class CurrentSubtotalPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)