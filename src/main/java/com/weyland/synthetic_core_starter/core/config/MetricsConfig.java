package com.weyland.synthetic_core_starter.core.config;

import com.weyland.synthetic_core_starter.service.CommandService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MetricsConfig {
    private final MeterRegistry meterRegistry;
    private final CommandService commandService;

    @PostConstruct
    public void setupMetrics() {
        Gauge.builder("synthetic.queue.size", commandService::getQueueSize)
                .description("Текущий размер очереди задач")
                .register(meterRegistry);
    }
}