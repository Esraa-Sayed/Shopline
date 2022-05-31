package com.eCommerce.shopify.ui.profile.view

import com.eCommerce.shopify.model.Order

interface OnOrderListner {
    fun onOrderClicked(order: Order)
}