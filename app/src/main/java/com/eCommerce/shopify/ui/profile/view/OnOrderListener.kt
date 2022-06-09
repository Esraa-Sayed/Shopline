package com.eCommerce.shopify.ui.profile.view

import com.eCommerce.shopify.model.orderDetails.Order

interface OnOrderListener {
    fun onOrderClicked(order: Order)
}