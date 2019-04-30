package com.br.flup.app.catalog.data

import com.belfortdev.hurbchallenge.core.extension.*
import com.br.flup.app.catalog.data.remote.CatalogRemoteDataSource
import com.br.flup.app.catalog.model.Product
import com.br.flup.app.core.data.AppScheduler
import com.br.flup.app.core.data.Outcome
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class CatalogRepository(private val compositeDisposable: CompositeDisposable) : CatalogDataContract.Repository {

    private val remote = CatalogRemoteDataSource
    private val scheduler = AppScheduler()

    override val fetchProductsOutcome: PublishSubject<Outcome<List<Product>>> =
        PublishSubject.create<Outcome<List<Product>>>()

    override fun fetchProducts() {
        fetchProductsOutcome.loading(true)
        remote.getCatalogProducts()
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                { productResultList ->
                    val productList = productResultList as List<Product>
                    fetchProductsOutcome.success(productList)
                },
                { exception ->
                    fetchProductsOutcome.error(exception)
                }
            ).addTo(compositeDisposable)
    }

}