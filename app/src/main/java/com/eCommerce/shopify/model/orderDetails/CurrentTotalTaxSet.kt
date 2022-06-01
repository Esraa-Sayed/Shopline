package com.eCommerce.shopify.model.orderDetails

data class CurrentTotalTaxSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)