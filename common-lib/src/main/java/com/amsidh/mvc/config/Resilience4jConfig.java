package com.amsidh.mvc.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Resilience4jConfig {

    @Bean
    public RegistryEventConsumer<Retry> getResiERegistryEventConsumer() {
        return new RegistryEventConsumer<Retry>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> log.info("Retry event {} retrying {} times for an error {}", event.getName(), event.getNumberOfRetryAttempts(), event.getLastThrowable().getMessage()));
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {

            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {

            }
        };
    }

    @Bean
    public RegistryEventConsumer<CircuitBreaker> getCircuitBreakerRegistryEventConsumer() {
        return new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                CircuitBreaker circuitBreaker = entryAddedEvent.getAddedEntry();
                circuitBreaker.getEventPublisher().onStateTransition(this::onStateTransition);
                circuitBreaker.getEventPublisher().onEvent(event -> onEvent(circuitBreaker, event));
            }

            private void onStateTransition(CircuitBreakerOnStateTransitionEvent event) {
                log.info(String.format("%s state : %s", event.getCircuitBreakerName(), event));
            }

            private void onEvent(CircuitBreaker cb, CircuitBreakerEvent event) {
                log.info(String.format("%s event(%s) : %s", event.getCircuitBreakerName(), cb.getState(), event));
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
                entryRemoveEvent.getRemovedEntry().getEventPublisher().onEvent(event -> log.info(event.toString()));
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
                entryReplacedEvent.getOldEntry().getEventPublisher().onEvent(event -> log.info(event.toString()));
                entryReplacedEvent.getNewEntry().getEventPublisher().onEvent(event -> log.info(event.toString()));
            }
        };
    }
}
