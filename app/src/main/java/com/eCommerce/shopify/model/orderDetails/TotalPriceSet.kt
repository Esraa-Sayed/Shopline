package com.eCommerce.shopify.model.orderDetails

data class TotalPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)