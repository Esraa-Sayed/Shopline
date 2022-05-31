package com.eCommerce.shopify.ui.profile.view

import com.eCommerce.shopify.model.OrderModel

interface OnOrderListner {
    fun onOrderClicked(order: OrderModel)
}