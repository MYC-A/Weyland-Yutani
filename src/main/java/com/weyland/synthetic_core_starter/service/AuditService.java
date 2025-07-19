package com.weyland.synthetic_core_starter.service;

import com.weyland.synthetic_core_starter.annotation.WeylandWatchingYou;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${synthetic.audit-topic:audit-topic}")
    private final String auditTopic;

    public void audit(WeylandWatchingYou.AuditMode mode, String method, Object[] args, Object result, Throwable error) {
        String message = buildMessage(method, args, result, error);

        try {
            if (mode == WeylandWatchingYou.AuditMode.KAFKA) {
                kafkaTemplate.send(auditTopic, message);
                log.debug("Отправлено аудит-сообщение в топик {}: {}", auditTopic, message);
            } else {
                log.info("Аудит: {}", message);
            }
        } catch (Exception e) {
            log.error("Ошибка при отправке аудит-сообщения в топик {}: {}", auditTopic, e.getMessage(), e);
            throw new RuntimeException("Не удалось отправить аудит-сообщение в Kafka", e);
        }
    }

    private String buildMessage(String method, Object[] args, Object result, Throwable error) {
        return String.format("Метод: %s | Аргументы: %s | Результат: %s | Ошибка: %s",
                method,
                Arrays.toString(args),
                result != null ? result : "null",
                error != null ? error.getMessage() : "нет");
    }
}