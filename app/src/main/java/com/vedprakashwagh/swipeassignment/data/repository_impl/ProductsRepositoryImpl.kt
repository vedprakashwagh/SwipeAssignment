package com.vedprakashwagh.swipeassignment.data.repository_impl

import com.vedprakashwagh.swipeassignment.data.remote.SwipeApi
import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.domain.model.repository.ProductsRepository
import io.ktor.client.statement.HttpResponse

class ProductsRepositoryImpl(private val api: SwipeApi) : ProductsRepository {
    override suspend fun getProducts(): List<Product> {
        return api.getProducts()
    }

    override suspend fun addProduct(product: Product): HttpResponse {
        return api.addProduct(product)
    }

}