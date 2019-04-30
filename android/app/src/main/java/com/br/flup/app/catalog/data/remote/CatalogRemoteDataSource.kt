package com.br.flup.app.catalog.data.remote

import com.belfortdev.hurbchallenge.core.extension.mapNetworkErrors
import com.br.flup.app.catalog.data.CatalogDataContract
import com.br.flup.app.catalog.model.ProductResult
import com.br.flup.app.core.data.remote.APIFactory
import io.reactivex.Single

object CatalogRemoteDataSource : CatalogDataContract.Remote {

    private val flupAPIService = APIFactory.flupAPIService

    override fun getCatalogProducts(): Single<List<ProductResult>> =
        flupAPIService
            .getProducts()
            .mapNetworkErrors()
            .map { it.map { productResponse -> productResponse.asDomain() } }

}