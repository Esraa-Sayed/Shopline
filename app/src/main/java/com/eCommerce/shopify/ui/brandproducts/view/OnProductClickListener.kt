package com.eCommerce.shopify.ui.brandproducts.view

import com.eCommerce.shopify.model.Product

interface OnProductClickListener {
    fun onProductItemClick(productId:Long)
    fun onFavBtnClick(product: Product)
}