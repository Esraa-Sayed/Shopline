package com.eCommerce.shopify.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.eCommerce.shopify.BuildConfig
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.orderDetails.LineItem

object AppConstants {

    const val BASE_URL: String = BuildConfig.BASE_URL
    const val API_KEY: String = BuildConfig.API_KEY
    const val PASSWORD: String = BuildConfig.PASSWORD
    const val IS_LOGIN: String = "Is_login"
    const val PREFRENCE_File = "User_Data"
    const val USER_ID: String = "User_id"
    const val USER_EMAIL: String = "User_email"
    const val USER_NAME: String = "User_name"
    const val CURRENCY: String = "Currency"

    const val SPLASH_TIME_OUT: Long = 3000
    const val MIN = 0
    const val MAX = 5
    const val SHOES = "SHOES"
    const val ACCESSORIES = "ACCESSORIES"
    const val T_SHIRTS = "T-SHIRTS"

    fun showAlert(context: Context, title: Int, message: String, icon: Int) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .setIcon(icon)
            .show()
    }

    fun playAnimation(view: View, context: Context, animation: Int) {
        view.startAnimation(AnimationUtils.loadAnimation(context, animation))
    }
    /*fun convertFromProdcutsToLineItems(products: Array<Product>):List<LineItem>{
        var items:List<LineItem> = emptyList()
        for (product in products){
            val lineItem = LineItem(variant_id = product.variants[0].id, price = product.variants[0].price)
        }
        return items
    }*/

}