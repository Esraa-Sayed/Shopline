package com.eCommerce.shopify.ui

import com.eCommerce.shopify.model.Product

interface OnProductClickListener {
    fun onProductItemClick()
    fun onFavBtnClick(product: Product)
}