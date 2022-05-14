package com.amsidh.mvc.propagator;

import io.github.resilience4j.core.ContextPropagator;
import org.slf4j.MDC;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

// This class is used to propagate MDC context in retry and circuit breaker calls
public class MDCContextPropagator implements ContextPropagator<Map<String, String>> {

    @Override
    public Supplier<Optional<Map<String, String>>> retrieve() {
        return () -> Optional.ofNullable(MDC.getCopyOfContextMap());
    }

    @Override
    public Consumer<Optional<Map<String, String>>> copy() {
        return (copyOfContextMap) -> copyOfContextMap.ifPresent(MDC::setContextMap);
    }

    @Override
    public Consumer<Optional<Map<String, String>>> clear() {
        return (contextMap) -> MDC.clear();
    }
}
