package com.vedprakashwagh.swipeassignment.di.module

import com.vedprakashwagh.swipeassignment.data.remote.SwipeApi
import com.vedprakashwagh.swipeassignment.data.repository_impl.ProductsRepositoryImpl
import com.vedprakashwagh.swipeassignment.domain.model.repository.ProductsRepository
import com.vedprakashwagh.swipeassignment.domain.use_case.products_screen.AddProductsUseCase
import com.vedprakashwagh.swipeassignment.domain.use_case.products_screen.GetProductsUseCase
import com.vedprakashwagh.swipeassignment.ui.products.ProductListViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val platformDependency = module {
    single {
        ktorHttpClient
    }
}

val apiDependency = module {
    single {
        SwipeApi(get())
    }
}

val repoDependency = module {
    single<ProductsRepository> {
        ProductsRepositoryImpl(get())
    }
}

val useCaseDependency = module {
    single {
        GetProductsUseCase(get())
    }

    single {
        AddProductsUseCase(get())
    }
}

val viewmodelDependency = module {
    viewModel {
        ProductListViewmodel()
    }
}