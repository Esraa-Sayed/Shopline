package com.eCommerce.shopify.ui.profile.view

import com.eCommerce.shopify.ui.favorite.model.Product

interface OnProductListner {
    fun onProductClicked(product: Product)
}