package com.weyland.synthetic_core_starter.service;

import com.weyland.synthetic_core_starter.DTO.CommandDTO;
import com.weyland.synthetic_core_starter.core.ThreadPoolConfigProperties;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CommandServiceImpl implements CommandService{
    private final ThreadPoolExecutor executor;

    private final MeterRegistry meterRegistry;

    public CommandServiceImpl(ThreadPoolExecutor executor, MeterRegistry meterRegistry) {
        this.executor = executor;
        this.meterRegistry = meterRegistry;
    }

    private ThreadPoolExecutor createExecutor(ThreadPoolConfigProperties config){
        return new ThreadPoolExecutor(
                config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(config.getQueueCapacity()),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Override
    public void processCommand(CommandDTO command) {
        try {
            executor.submit( () -> {
                log.info("Выполняется комманда: description={}, priority={}, author={}, time={}",
                        command.getDescription(), command.getPriority(), command.getAuthor(), command.getTime());
                meterRegistry.counter("task.completed",
                        "author", command.getAuthor(),
                               "priority", command.getPriority().name()).increment();
            } );
        }
        catch (Exception e){
            log.error("Ошибка при добавлении задачи: {}", command, e);
        }
    }

    @Override
    public int getQueueSize() {
        return executor.getQueue().size();
    }
}
