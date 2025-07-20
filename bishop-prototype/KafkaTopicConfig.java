package com.REST_API.bishop_prototype.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${synthetic.audit-topic:audit-topic}")
    private String auditTopic;

    @Bean
    public NewTopic auditTopic() {
        return new NewTopic(auditTopic, 1, (short) 1);
    }
}