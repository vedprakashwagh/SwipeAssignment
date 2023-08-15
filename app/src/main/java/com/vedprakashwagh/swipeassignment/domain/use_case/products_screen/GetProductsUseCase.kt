package com.vedprakashwagh.swipeassignment.domain.use_case.products_screen

import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.domain.model.repository.ProductsRepository
import com.vedprakashwagh.swipeassignment.utils.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetProductsUseCase(private val repository: ProductsRepository) {

    operator fun invoke(): Flow<ResourceState<List<Product>, Exception?>> = flow {
        try {
            emit(ResourceState.Loading())
            val productsData = repository.getProducts()
            emit(ResourceState.Success(productsData))
        } catch (e: IOException) {
            emit(ResourceState.Failed(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(ResourceState.Failed(e))
        }

    }

}