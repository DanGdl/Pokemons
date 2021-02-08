package com.mdgd.pokemon.models.infra

import com.google.common.base.Optional

class Result<T> {
    private val value: Optional<T>
    private val error: Optional<Throwable>

    constructor(value: T) {
        this.value = Optional.of(value)
        error = Optional.absent()
    }

    constructor(error: Throwable) {
        value = Optional.absent()
        this.error = Optional.of(error)
    }

    fun isError(): Boolean {
        return !value.isPresent
    }

    fun getValue(): T {
        return value.get()
    }

    fun getError(): Throwable {
        return error.get()
    }
}
