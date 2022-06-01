package com.eCommerce.shopify.ui.profile.view

import com.eCommerce.shopify.model.OrderDetailsItems
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.model.Product

interface OnProductListner {
    fun onProductClicked(product: Product)
}