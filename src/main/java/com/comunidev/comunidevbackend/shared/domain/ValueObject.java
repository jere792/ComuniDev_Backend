package com.comunidev.comunidevbackend.shared.domain;

import java.util.Objects;

public abstract class ValueObject<T> {
    protected abstract T[] getEqualityComponents();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject<?> that = (ValueObject<?>) o;
        return Objects.deepEquals(getEqualityComponents(), that.getEqualityComponents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEqualityComponents());
    }
}
