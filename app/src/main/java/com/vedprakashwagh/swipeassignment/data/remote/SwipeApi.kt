package com.vedprakashwagh.swipeassignment.data.remote

import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.append
import io.ktor.http.appendPathSegments
import java.net.URL

class SwipeApi(private val httpClient: HttpClient) {

    suspend fun getProducts(): List<Product> = httpClient.get(Constants.Endpoints.ENDPOINT) {
        accept(ContentType.Any)
        url {
            appendPathSegments(Constants.Endpoints.GET)
        }
    }.body()

    suspend fun addProduct(product: Product) =
        httpClient.submitForm(Constants.Endpoints.ENDPOINT, formParameters = Parameters.build {
            append("image", "")
            append("product_name", product.productName ?: "")
            append("product_type", product.productType ?: "")
            append("price", product.price.toString())
            append("tax", product.tax.toString())
        }) {
            url {
                appendPathSegments(Constants.Endpoints.ADD)
            }
        }

}