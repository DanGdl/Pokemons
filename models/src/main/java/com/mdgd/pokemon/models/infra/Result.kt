package com.mdgd.pokemon.models.infra

// TODO: use result from kotlin
class Result<T> {
    private val value: T?
    private val error: Throwable?

    constructor(value: T) {
        this.value = value
        error = null
    }

    constructor(error: Throwable) {
        value = null
        this.error = error
    }

    fun isError() = error != null
    fun hasValue() = error == null

    fun getValue() = value!!

    fun getError() = error!!
}
