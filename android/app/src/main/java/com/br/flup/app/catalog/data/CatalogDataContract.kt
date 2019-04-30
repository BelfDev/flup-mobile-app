package com.br.flup.app.catalog.data

import com.br.flup.app.catalog.model.Product
import com.br.flup.app.catalog.model.ProductResult
import com.br.flup.app.core.data.Outcome
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface CatalogDataContract {

    interface Repository {
        val fetchProductsOutcome: PublishSubject<Outcome<List<Product>>>

        fun fetchProducts()
    }

    interface Remote {
        fun getCatalogProducts(): Single<List<ProductResult>>
    }

}