package com.example.platform.library.rest.controller;

import com.example.platform.library.metrics.MetricsHandler;
import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRestController {
  @Autowired protected Config appConfig;
  @Autowired protected MetricsHandler metricsHandler;
}
