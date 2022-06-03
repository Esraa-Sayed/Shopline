package com.eCommerce.shopify.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eCommerce.shopify.database.favorite.FavoriteDao
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartDao
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.utils.ShopifyConverters

@Database(entities = [Product::class, ProductDetail::class], version = 1)
@TypeConverters(ShopifyConverters::class)
abstract class ShopifyDatabase:RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object{
        private var shopifyDatabase:ShopifyDatabase? = null

        fun getInstance(context: Context):ShopifyDatabase{
            return shopifyDatabase?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, ShopifyDatabase::class.java, "ShopifyDatabase").build()
                shopifyDatabase = instance
                instance
            }
        }
    }
}