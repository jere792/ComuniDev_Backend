package com.comunidev.comunidevbackend.shared.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RealtimeEventPublisher {

    private final Map<String, Sinks.Many<Object>> notificationSinks = new ConcurrentHashMap<>();
    private final Map<String, Sinks.Many<Object>> connectionSinks = new ConcurrentHashMap<>();
    private final Map<String, Sinks.Many<Object>> messageSinks = new ConcurrentHashMap<>();

    public void publishConnectionStatus(String userId, Object payload) {
        sinkFor(connectionSinks, userId).tryEmitNext(payload);
    }

    public void publishNotification(String userId, Object payload) {
        sinkFor(notificationSinks, userId).tryEmitNext(payload);
    }

    public void publishMessage(String userId, Object payload) {
        sinkFor(messageSinks, userId).tryEmitNext(payload);
    }

    public Flux<Object> notificationStream(String userId) {
        return streamFor(notificationSinks, userId);
    }

    public Flux<Object> connectionStatusStream(String userId) {
        return streamFor(connectionSinks, userId);
    }

    public Flux<Object> messageStream(String userId) {
        return streamFor(messageSinks, userId);
    }

    private Sinks.Many<Object> sinkFor(Map<String, Sinks.Many<Object>> map, String key) {
        return map.computeIfAbsent(key, k -> Sinks.many().multicast().onBackpressureBuffer(64, false));
    }

    private Flux<Object> streamFor(Map<String, Sinks.Many<Object>> map, String key) {
        return sinkFor(map, key).asFlux();
    }
}
