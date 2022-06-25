package com.eCommerce.shopify.network

import com.eCommerce.shopify.model.*
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.network.RemoteSource
import retrofit2.Response

class FakeRemoteSource: RemoteSource {

    private var smartCollectionsBrand: SmartCollectionsBrand? = null
    private var customCollectionsCategory: CustomCollectionsCategory? = null
    private var products: Products? = null
    private var productDetails: ProductDetails? = null

    constructor(smartCollectionsBrand: SmartCollectionsBrand = SmartCollectionsBrand(mutableListOf())) {
        this.smartCollectionsBrand = smartCollectionsBrand
    }

    constructor(customCollectionsCategory: CustomCollectionsCategory = CustomCollectionsCategory(mutableListOf())) {
        this.customCollectionsCategory = customCollectionsCategory
    }

    constructor(products: Products = Products(mutableListOf())) {
        this.products = products
    }

    constructor(productDetails: ProductDetails) {
        this.productDetails = productDetails
    }

    override suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand> {
        return Response.success(smartCollectionsBrand)
    }

    override suspend fun getUserWithEmail(userEmail: String): Response<UserData> {
        TODO("Not yet implemented")
    }

    override suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory> {
        return Response.success(customCollectionsCategory)
    }

    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return Response.success(products)
    }

    override suspend fun getProductDetails(id: Long): Response<ProductDetails> {
        return Response.success(productDetails)
    }

    override suspend fun getCollectionWithId(vendor: String): Response<BrandProductsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun registerCustomer(customer: CustomerResponse): Response<CustomerResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserAddresses(id: Long): Response<AddressesUserModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserOrders(id: Long): Response<OrderModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(id: Long, customer: Customer): Response<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun postOrder(order: OrderDetails): Response<OrderDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiscountCodes(): Response<DiscountCodes> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProducts(): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun addAddress(
        userId: Long,
        address: PostAddress
    ): Response<AddressesUserModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddress(userId: Long, addressId: Long) {
        TODO("Not yet implemented")
    }
}