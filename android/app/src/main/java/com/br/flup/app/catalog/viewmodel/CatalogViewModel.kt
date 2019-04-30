package com.br.flup.app.catalog.viewmodel

import androidx.lifecycle.LiveData
import com.belfortdev.hurbchallenge.core.extension.toLiveData
import com.br.flup.app.catalog.data.CatalogRepository
import com.br.flup.app.catalog.model.Product
import com.br.flup.app.core.data.Outcome
import com.br.flup.app.core.viewmodel.DisposingViewModel

class CatalogViewModel : DisposingViewModel() {

    private val repo = CatalogRepository(compositeDisposable)

    val fetchProductsOutcome: LiveData<Outcome<List<Product>>> by lazy {
        repo.fetchProductsOutcome.toLiveData(compositeDisposable)
    }

    fun fetchProducts() {
        repo.fetchProducts()
    }

}