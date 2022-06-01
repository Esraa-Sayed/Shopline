package com.eCommerce.shopify.ui.order.view
import com.eCommerce.shopify.model.Order

interface OnOrderRowClicked {
    fun onRowClickedListener(order: Order)
}