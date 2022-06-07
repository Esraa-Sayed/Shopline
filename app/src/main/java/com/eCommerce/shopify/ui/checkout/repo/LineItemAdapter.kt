package com.eCommerce.shopify.ui.checkout.repo

import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.orderDetails.LineItem

object LineItemAdapter {
    fun convertProductDetailIntoLineItem(productsToCheckout:Array<ProductDetail>):List<LineItem>{
        var lineItems: MutableList<LineItem> = mutableListOf()

        for(item in productsToCheckout){
            var lineItem = LineItem(variant_id = item.variants[0].id, quantity = item.amount, price = item.variants[0].price)
            lineItems.add(lineItem)
        }
        return  lineItems
    }
}