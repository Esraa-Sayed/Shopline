package com.eCommerce.shopify.model.orderDetails

data class PriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)