package com.br.flup.app.core.model

interface DomainMappable<R> {
    fun asDomain(): R
}