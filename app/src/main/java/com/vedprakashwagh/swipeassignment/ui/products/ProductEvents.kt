package com.vedprakashwagh.swipeassignment.ui.products

import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product

sealed class ProductEvents{

    object FetchProducts: ProductEvents()

    data class AddProduct(var product: Product): ProductEvents()

    data class SearchProduct(var searchTerm: String): ProductEvents()

}
