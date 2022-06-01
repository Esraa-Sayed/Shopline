package com.eCommerce.shopify.model.orderDetails

data class CurrentTotalDiscountsSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)