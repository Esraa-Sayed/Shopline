package com.eCommerce.shopify.utils

import android.graphics.Path
import androidx.room.TypeConverter
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Option
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.Variant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShopifyConverters {
    var gson = Gson()

    @TypeConverter
    fun productToString(product:Product):String{
        return gson.toJson(product)
    }

    @TypeConverter
    fun stringToProduct(productString:String):Product{
        return gson.fromJson(productString,Product::class.java)
    }

    @TypeConverter
    fun variantToString(variant: Variant):String{
        return gson.toJson(variant)
    }

    @TypeConverter
    fun stringToVariant(variantString:String):Variant{
        return gson.fromJson(variantString,Variant::class.java)
    }

    @TypeConverter
    fun optionToString(option : Option) : String{
        return gson.toJson(option)
    }

    @TypeConverter
    fun stringToOption(optionString : String) : Option{
        return gson.fromJson(optionString, Option::class.java)
    }

    @TypeConverter
    fun imgToString(img : ImageProduct) : String{
        return gson.toJson(img)
    }

    @TypeConverter
    fun stringToImg(ImgString : String) : ImageProduct{
        return gson.fromJson(ImgString, ImageProduct::class.java)
    }

    @TypeConverter
    fun productListToString(productList:List<Product>):String{
        return gson.toJson(productList)
    }
    @TypeConverter
    fun stringToProductList(productListString:String):List<Product>{
        var list = object : TypeToken<List<Product>>(){}.type
        return gson.fromJson(productListString,list)
    }

    @TypeConverter
    fun optionListToString(optionList:List<Option>):String{
        return gson.toJson(optionList)
    }
    @TypeConverter
    fun stringToOptionList(optionListString:String):List<Option>{
        var list = object : TypeToken<List<Option>>(){}.type
        return gson.fromJson(optionListString,list)
    }

    @TypeConverter
    fun variantListToString(variantList:List<Variant>):String{
        return gson.toJson(variantList)
    }
    @TypeConverter
    fun stringToVariantList(variantListString:String):List<Variant>{
        var list = object : TypeToken<List<Variant>>(){}.type
        return gson.fromJson(variantListString,list)
    }

    @TypeConverter
    fun imgListToString(imgList:List<ImageProduct>):String{
        return gson.toJson(imgList)
    }
    @TypeConverter
    fun stringToImgList(imgListString:String):List<ImageProduct>{
        var list = object : TypeToken<List<ImageProduct>>(){}.type
        return gson.fromJson(imgListString,list)
    }

}