package com.eCommerce.shopify.ui.shopping_cart.view

import com.eCommerce.shopify.model.ProductDetail

interface Listner {
    fun update(productDetail: ProductDetail)
    fun checkToDelete(productDetail: ProductDetail)
}