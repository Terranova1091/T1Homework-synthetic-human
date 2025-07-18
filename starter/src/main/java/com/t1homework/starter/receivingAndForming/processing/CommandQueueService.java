package com.t1homework.starter.receivingAndForming.processing;

import com.t1homework.starter.receivingAndForming.processing.exception.QueueOverflowException;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommandQueueService {

    // Интервал обработки (мс)
    // Предполагаем, что каждая задача исполняется 10сек.
    private static final int PROCESSING_INTERVAL_MS = 10000;

    //Вместимость очереди
    private static final int QUEUE_CAPACITY = 100;

    //Кол-во потоков (В контексте является колличеством роботов, которые могут исполнять задания)
    private static final int NUMBER_OF_THREADS = 1;

    //Задержка перед началом обработки
    private static final int INITIAL_DELAY = 0;


    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    private final CounterMap counterMap;

    public CommandQueueService(MeterRegistry meterRegistry) {
        this.counterMap = new CounterMap(meterRegistry);
        Gauge.builder("android.queue.size", queue, Collection::size).description("Текущий размер очереди задач")
                .register(meterRegistry);
        this.startQueueProcessor();
    }

    public void incrementAuthorCounter(String author) {
        counterMap.increment(author);
    }

    public void addTask(Runnable task) throws QueueOverflowException {
        if (!queue.offer(task)) {
            throw new QueueOverflowException("Очередь переполнена!");
        } else {

            log.info("Задание добавлено.\n Кол-во элементов в очереди: {}", queue.size());
        }
    }

    private void startQueueProcessor() {

        scheduler.scheduleWithFixedDelay(() -> {
            if (!queue.isEmpty() && isProcessing.compareAndSet(false, true)) {
                try {
                    processNextTask();
                } finally {
                    isProcessing.set(false);
                }
            }

        }, INITIAL_DELAY, PROCESSING_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    private void processNextTask() {
        Runnable task = queue.poll();
        if (task != null) {
            try {
                task.run();
            } catch (Exception e) {
                log.error("Ошибка задачи", e);
            }
        }

        log.info("Задание выполнено.\n Кол-во элементов в очереди: {}", queue.size());
    }

}
