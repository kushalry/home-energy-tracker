package com.kushal.ingestion_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kushal.ingestion_service.dto.EnergyUsageDto;
import com.kushal.kafka.event.EnergyUsageEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngestionService {

    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public IngestionService(KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void ingestEnergyUsage(EnergyUsageDto input) {
        // Convert DTO to Event
        EnergyUsageEvent event = EnergyUsageEvent.builder()
                .deviceId(input.deviceId())
                .energyConsumed(input.energyConsumed())
                .timestamp(input.timestamp())
                .build();

        // Send to Kafka Topic
        kafkaTemplate.send("energy-usage", event);
        log.info("Ingested Energy Usage Event: {}", event);
    }

}
