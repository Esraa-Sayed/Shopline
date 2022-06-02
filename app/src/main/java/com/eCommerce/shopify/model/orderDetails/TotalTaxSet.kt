package com.eCommerce.shopify.model.orderDetails

data class TotalTaxSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)