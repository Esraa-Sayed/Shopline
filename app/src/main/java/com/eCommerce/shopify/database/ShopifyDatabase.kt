package com.eCommerce.shopify.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.utils.ShopifyConverters

@Database(entities = arrayOf(Product::class), version = 1)
@TypeConverters(ShopifyConverters::class)
abstract class ShopifyDatabase:RoomDatabase() {

    abstract fun favoriteDao():FavoriteDao

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