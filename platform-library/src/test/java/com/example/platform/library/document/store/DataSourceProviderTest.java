package com.example.platform.library.document.store;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.platform.library.document.store.mongo.MongoSyncDataSource;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DataSourceProviderTest {
  @Test
  void testShouldReturnMongoDataSource() {
    DataSource dataSource =
        DataSourceProvider.getDataSource(DataSourceConfig.from(getMongoDataSourceConfig()));
    assertEquals(MongoSyncDataSource.class, dataSource.getClass());

    // should return the same dataSource
    assertEquals(
        dataSource,
        DataSourceProvider.getDataSource(DataSourceConfig.from(getMongoDataSourceConfig())));
  }

  private Config getMongoDataSourceConfig() {
    return ConfigFactory.parseMap(
        Map.of("type", "mongodb", "uri", "mongodb://localhost:27017", "databaseName", "database"));
  }
}
