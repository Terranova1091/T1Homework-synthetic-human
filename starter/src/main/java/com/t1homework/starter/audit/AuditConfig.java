package com.t1homework.starter.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableAspectJAutoProxy
public class AuditConfig {
    public AuditConfig() {
    }

    @Bean
    @ConditionalOnProperty(
            name = {"weyland.audit.mode"},
            havingValue = "KAFKA"
    )
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
