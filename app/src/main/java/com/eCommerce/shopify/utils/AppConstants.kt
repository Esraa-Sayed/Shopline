package com.eCommerce.shopify.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.eCommerce.shopify.BuildConfig
import com.eCommerce.shopify.R

object AppConstants {

    const val BASE_URL: String = BuildConfig.BASE_URL

    const val SPLASH_TIME_OUT: Long = 3000

    fun showAlert(context: Context, title: Int, message: String, icon: Int) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .setIcon(icon)
            .show()
    }
}