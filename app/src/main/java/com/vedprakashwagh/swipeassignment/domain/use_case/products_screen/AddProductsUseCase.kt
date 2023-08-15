package com.vedprakashwagh.swipeassignment.domain.use_case.products_screen

import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.domain.model.repository.ProductsRepository
import com.vedprakashwagh.swipeassignment.utils.ResourceState
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AddProductsUseCase(private val repository: ProductsRepository) {

    operator fun invoke(product: Product): Flow<ResourceState<HttpResponse, Exception?>> = flow {
        try {
            emit(ResourceState.Loading())
            val productsData = repository.addProduct(product)
            if (productsData.status.value == HttpStatusCode.OK.value) {
                emit(ResourceState.Success(productsData))
            } else {
                emit(ResourceState.Failed(Exception("An error occurred while adding the product. Error code: ${productsData.status.value} ${productsData.status.description}")))
            }

        } catch (e: IOException) {
            emit(ResourceState.Failed(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(ResourceState.Failed(e))
        }

    }

}