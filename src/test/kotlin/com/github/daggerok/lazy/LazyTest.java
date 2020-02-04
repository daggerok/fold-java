package com.github.daggerok.lazy;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class Lazy<T> {
    private T value;
    private final Supplier<T> supplier;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (value == null) value = supplier.get();
        return value;
    }
}

public class LazyTest {
    @Test
    void test() {
        AtomicLong atomicLong = new AtomicLong(5);
        Lazy<String> lazy = new Lazy<>(() -> "a long value is: " + atomicLong.incrementAndGet());
        assertThat(lazy.get(), equalTo("a long value is: 6"));
        assertThat(lazy.get(), equalTo("a long value is: 6"));
        assertThat(atomicLong.get(), equalTo(6L));
    }
}
