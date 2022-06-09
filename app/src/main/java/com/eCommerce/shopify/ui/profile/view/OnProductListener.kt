package com.eCommerce.shopify.ui.profile.view

import com.eCommerce.shopify.model.Product

interface OnProductListener {
    fun onProductClicked(product: Product)
    fun onFavBtnClick(product: Product)
}