package com.example.launcher;

import com.example.platform.library.metrics.MetricsHandler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.example.hello.world"})
public class BeanConfiguration {

  @Autowired public PrometheusMeterRegistry prometheusMeterRegistry;

  @Bean
  public Config getConfig() {
    Config config = ConfigFactory.load("application.yaml");
    return config;
  }

  @Bean
  public MetricsHandler getMetricsHandler() {
    return new MetricsHandler(prometheusMeterRegistry);
  }
}
