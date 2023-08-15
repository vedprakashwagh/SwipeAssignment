package com.vedprakashwagh.swipeassignment.domain.model.repository

import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import io.ktor.client.statement.HttpResponse

interface ProductsRepository {

    suspend fun getProducts(): List<Product>

    suspend fun addProduct(product: Product): HttpResponse
}