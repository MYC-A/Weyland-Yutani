package com.weyland.starter.service;

import com.weyland.starter.DTO.CommandDTO;
import com.weyland.starter.core.config.ThreadPoolConfigProperties;
import com.weyland.starter.core.exception.CommandServiceException;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CommandServiceImpl implements CommandService{
    private final ThreadPoolExecutor executor;

    private final MeterRegistry meterRegistry;



    public CommandServiceImpl(MeterRegistry meterRegistry, ThreadPoolConfigProperties config) {
        this.meterRegistry = meterRegistry;
        this.executor = new ThreadPoolExecutor(
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
            throw new CommandServiceException("Ошибка при обработке задач", e);

        }
    }

    @Override
    public int getQueueSize() {
        return executor.getQueue().size();
    }
}
