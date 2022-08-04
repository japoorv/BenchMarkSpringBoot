package com.example.platform.library.document.store;

import com.example.platform.library.document.store.mongo.MongoSyncDataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceProvider {
  private static Map<DataSourceConfig, DataSource> dataSourceMap = new HashMap<>();

  public static DataSource getDataSource(DataSourceConfig config) {
    String type = config.getType().toLowerCase();
    switch (type) {
      case "mongodb":
        return dataSourceMap.computeIfAbsent(
            config, dataSourceConfig -> new MongoSyncDataSource(dataSourceConfig));
      default:
        throw new RuntimeException("Datasource " + type + " not found");
    }
  }
}
