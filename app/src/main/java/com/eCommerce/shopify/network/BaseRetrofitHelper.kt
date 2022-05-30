package com.eCommerce.shopify.network

import com.eCommerce.shopify.utils.AppConstants.API_KEY
import com.eCommerce.shopify.utils.AppConstants.BASE_URL
import com.eCommerce.shopify.utils.AppConstants.PASSWORD
import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseRetrofitHelper {

    fun getInstance(): Retrofit {

        val credentials = Credentials.basic(API_KEY, PASSWORD)

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .header("Authorization", credentials)
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val gson = GsonBuilder().serializeNulls().create()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}