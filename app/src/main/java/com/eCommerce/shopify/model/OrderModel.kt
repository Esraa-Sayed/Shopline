package com.eCommerce.shopify.model

import com.eCommerce.shopify.model.orderDetails.Order

data class OrderModel(
    val orders: List<Order>
)