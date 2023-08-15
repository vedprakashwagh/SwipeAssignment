package com.vedprakashwagh.swipeassignment

import android.app.Application
import android.content.Context
import com.vedprakashwagh.swipeassignment.di.module.apiDependency
import com.vedprakashwagh.swipeassignment.di.module.platformDependency
import com.vedprakashwagh.swipeassignment.di.module.repoDependency
import com.vedprakashwagh.swipeassignment.di.module.useCaseDependency
import com.vedprakashwagh.swipeassignment.di.module.viewmodelDependency
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SwipeAssignment: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this@SwipeAssignment)
    }

    private fun initKoin(context: Context) = startKoin {
        androidLogger()
        androidContext(context)


        modules(
            platformDependency,
            apiDependency,
            repoDependency,
            useCaseDependency,
            viewmodelDependency
        )
    }

}