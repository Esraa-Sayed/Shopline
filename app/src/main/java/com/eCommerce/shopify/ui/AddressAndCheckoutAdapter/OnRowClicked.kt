package com.eCommerce.shopify.ui.AddressAndCheckoutAdapter

import com.eCommerce.shopify.model.address

interface OnRowClicked {
    fun onRowClickedListener(address: address)
}