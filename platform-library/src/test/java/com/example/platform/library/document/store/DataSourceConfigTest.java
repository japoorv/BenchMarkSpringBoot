package com.example.platform.library.document.store;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.typesafe.config.ConfigFactory;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DataSourceConfigTest {
  @Test
  void testDataSourceConfig() {
    DataSourceConfig dataSourceConfig =
        DataSourceConfig.from(
            ConfigFactory.parseMap(
                Map.of("uri", "someUri", "type", "someType", "databaseName", "someDatabaseName")));
    assertEquals("someUri", dataSourceConfig.getUri());
    assertEquals("someType", dataSourceConfig.getType());
    assertEquals("someDatabaseName", dataSourceConfig.getDatabaseName());
  }
}
