package com.t1homework.starter.receivingAndForming.processing;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CounterMap {

    private final MeterRegistry registry;

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();


    public void increment(String author) {
        counters
                .computeIfAbsent(author, a ->
                        Counter.builder("android.tasks.completed")
                        .description("Выполнено заданий")
                        .tag("author", a)
                        .register(registry)
                )
                .increment();
    }
}

