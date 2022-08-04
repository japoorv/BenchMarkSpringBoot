package com.example.platform.library.document.store;

import com.typesafe.config.Config;
import lombok.Data;

@Data
public class DataSourceConfig {
  private final String uri;
  private final String type;
  private final String databaseName;

  public static DataSourceConfig from(Config dataSourceConfig) {
    return new DataSourceConfig(
        dataSourceConfig.getString("uri"),
        dataSourceConfig.getString("type"),
        dataSourceConfig.getString("databaseName"));
  }
}
