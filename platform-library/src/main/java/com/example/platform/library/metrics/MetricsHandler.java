package com.example.platform.library.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetricsHandler {
  private MeterRegistry meterRegistry;

  public MetricsHandler(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public Counter registerCounter(String name, Map<String, String> tags) {
    List<Tag> tagsList =
        tags.entrySet().stream()
            .map(entry -> Tag.of(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    return this.meterRegistry.counter(name, tagsList);
  }

  /** Closes the associated meter registry and release all the additional resources. */
  public void close() {
    this.meterRegistry.close();
  }
}
