package com.eCommerce.shopify.ui.order.view

import com.eCommerce.shopify.model.orderDetails.Order

interface OnOrderRowClicked {
    fun onRowClickedListener(order: Order)
}