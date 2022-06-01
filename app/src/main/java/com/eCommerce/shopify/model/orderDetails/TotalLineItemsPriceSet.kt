package com.eCommerce.shopify.model.orderDetails

data class TotalLineItemsPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)