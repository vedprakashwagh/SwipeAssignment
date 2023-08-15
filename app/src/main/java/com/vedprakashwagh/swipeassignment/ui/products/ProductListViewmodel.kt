package com.vedprakashwagh.swipeassignment.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.domain.use_case.products_screen.AddProductsUseCase
import com.vedprakashwagh.swipeassignment.domain.use_case.products_screen.GetProductsUseCase
import com.vedprakashwagh.swipeassignment.utils.ResourceState
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductListViewmodel : ViewModel(), KoinComponent {

    private val originalList = ArrayList<Product>()
    val state = MutableLiveData<ResourceState<List<Product>, Exception?>>()

    var stateAddProduct = MutableLiveData<ResourceState<HttpResponse, Exception?>>()

    fun onEvent(event: ProductEvents) = viewModelScope.launch {
        when (event) {
            is ProductEvents.AddProduct -> {
                if (event.product.isValid()) {
                    addProduct(event.product)
                }
            }

            is ProductEvents.FetchProducts -> {
                fetchProducts()
            }

            is ProductEvents.SearchProduct -> {
                searchProduct(event.searchTerm)
            }

        }
    }


    private fun searchProduct(searchTerm: String) {
        if (searchTerm.isBlank()) {
            state.postValue(ResourceState.Success(originalList))
        } else if (originalList.isNotEmpty() && searchTerm.isNotBlank()) {
            val filteredList = originalList.filter {
                it.productName?.lowercase()?.contains(searchTerm.lowercase()) ?: false
            }
            state.postValue(ResourceState.Success(filteredList))
        }
    }

    private fun fetchProducts() {
        val getProductsUseCase: GetProductsUseCase by inject()
        getProductsUseCase().onEach {
            /**
             * Preserving the original list we receive for now to reduce unnecessary network calls.
             * In real application we'll probably be clearing this list and fetching fresh data every time.
             */
            if (it.isSuccess()) {
                originalList.clear()
                originalList.addAll((it as ResourceState.Success).data)
            }
            state.postValue(it)
        }.launchIn(viewModelScope)
    }

    private fun addProduct(product: Product) {
        val addProductUseCase: AddProductsUseCase by inject()
        addProductUseCase(product).onEach {
            stateAddProduct.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun reInitialiseAddProductState(){
        stateAddProduct = MutableLiveData<ResourceState<HttpResponse, Exception?>>()
    }

}