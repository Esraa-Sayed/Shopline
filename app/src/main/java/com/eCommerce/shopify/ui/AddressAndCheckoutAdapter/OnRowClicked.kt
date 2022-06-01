package com.eCommerce.shopify.ui.AddressAndCheckoutAdapter

import com.eCommerce.shopify.model.Addresse

interface OnRowClicked {
    fun onRowClickedListener(address: Addresse)
}