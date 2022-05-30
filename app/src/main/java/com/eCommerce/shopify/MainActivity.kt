package com.eCommerce.shopify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.eCommerce.shopify.databinding.ActivityMainBinding
import com.eCommerce.shopify.ui.HomeActivity
import com.eCommerce.shopify.utils.AppConstants.SPLASH_TIME_OUT

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}