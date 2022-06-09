package com.eCommerce.shopify.ui.shopping_cart.view

import com.eCommerce.shopify.model.ProductDetail

interface Listener {
    fun update(productDetail: ProductDetail)
    fun checkToDelete(productDetail: ProductDetail)
    fun decrementTotalPrice(product: ProductDetail)
    fun incrementTotalPrice(product: ProductDetail)
}