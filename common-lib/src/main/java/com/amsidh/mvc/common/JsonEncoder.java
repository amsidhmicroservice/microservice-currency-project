package com.amsidh.mvc.common;

import ch.qos.logback.classic.spi.ILoggingEvent;
import net.logstash.logback.composite.*;
import net.logstash.logback.composite.SequenceJsonProvider;
import net.logstash.logback.composite.loggingevent.*;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;

public class JsonEncoder extends LoggingEventCompositeJsonEncoder {
    public JsonEncoder() {
    }
    @Override
    public void start() {
        JsonProviders<ILoggingEvent> providers = getProviders();
        providers.addProvider(new LoggingEventFormattedTimestampJsonProvider());
        providers.addProvider(new SequenceJsonProvider<>());
        providers.addProvider(new ContextJsonProvider<>());
        providers.addProvider(new MdcJsonProvider());
        providers.addProvider(new LogLevelJsonProvider());
        providers.addProvider(new LoggingEventThreadNameJsonProvider());
        providers.addProvider(new LogstashMarkersJsonProvider());
        providers.addProvider(new ArgumentsJsonProvider());
        providers.addProvider(new CallerDataJsonProvider());
        providers.addProvider(new MessageJsonProvider());
        providers.addProvider(new StackTraceJsonProvider());
        providers.addProvider(new RootStackTraceElementJsonProvider());
        super.start();
    }
}
