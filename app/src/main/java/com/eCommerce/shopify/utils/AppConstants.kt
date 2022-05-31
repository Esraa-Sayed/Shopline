package com.eCommerce.shopify.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.eCommerce.shopify.BuildConfig
import com.eCommerce.shopify.R

object AppConstants {

    const val BASE_URL: String = BuildConfig.BASE_URL
    const val API_KEY: String = BuildConfig.API_KEY
    const val PASSWORD: String = BuildConfig.PASSWORD
    const val IS_LOGIN: String = "Is_login"
    const val PREFRENCE_File = "User_Data"
    const val USER_ID: String = "User_id"
    const val USER_EMAIL: String = "User_email"

    const val SPLASH_TIME_OUT: Long = 3000

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
}