package com.example.platform.library.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetricsHandlerTest {
  private MetricsHandler metricsHandler;
  private MeterRegistry meterRegistry;

  @BeforeEach
  void setUp() {
    this.meterRegistry = new SimpleMeterRegistry();
    this.metricsHandler = new MetricsHandler(meterRegistry);
  }

  @AfterEach
  void tearDown() {
    this.metricsHandler.close();
  }

  @Test
  void testCounter() {
    Counter counter =
        this.metricsHandler.registerCounter("someName", Map.of("key1", "val1", "key2", "val2"));
    counter.increment();

    assertEquals(1, this.meterRegistry.getMeters().size());
    assertEquals(counter, this.meterRegistry.getMeters().get(0));
  }
}
