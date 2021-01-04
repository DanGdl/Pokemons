package com.mdgd.pokemon.models.infra;

import com.google.common.base.Optional;

public class Result<T> {
    private final Optional<T> value;
    private final Optional<Throwable> error;

    public Result(T value) {
        this.value = Optional.of(value);
        this.error = Optional.absent();
    }

    public Result(Throwable error) {
        this.value = Optional.absent();
        this.error = Optional.of(error);
    }

    public boolean isError() {
        return !value.isPresent();
    }

    public T getValue() {
        return value.get();
    }

    public Throwable getError() {
        return error.get();
    }

    public boolean hasValue() {
        return value.isPresent();
    }
}
