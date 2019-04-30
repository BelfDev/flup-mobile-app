package com.br.flup.app.catalog.model

interface ProductResult

data class Product(
    val id: String,
    val category: String?,
    val name: String,
    val price: Int,
    val visible: Boolean,
    val image: String,
    val isMealProduct: Boolean,
    val lowStock: Int,
    val stock: Int
) : ProductResult
