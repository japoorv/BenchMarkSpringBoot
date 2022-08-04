package com.example.platform.library.document.store.mongo;

import static junit.framework.TestCase.assertEquals;

import com.example.platform.library.document.store.Collection;
import com.example.platform.library.document.store.DataSourceConfig;
import com.example.platform.library.document.store.DataSourceProvider;
import com.example.platform.library.document.store.Filter;
import com.example.platform.library.document.store.Query;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoSyncDataSourceTest {

  private MongoDBContainer mongoDBContainer;
  private String connectionString;
  private MongoSyncDataSource mongoSyncDataSource;
  private static final String DB_NAME = "database";

  @BeforeEach
  void setUp() {
    mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    mongoDBContainer.start();
    connectionString = mongoDBContainer.getConnectionString();
    mongoSyncDataSource =
        (MongoSyncDataSource)
            DataSourceProvider.getDataSource(DataSourceConfig.from(getMongoDataSourceConfig()));
  }

  @AfterEach
  void tearDown() {
    mongoDBContainer.stop();
  }

  @Test
  void testMongoSyncDataSource() {
    Collection<SampleDoc> collection =
        mongoSyncDataSource.getCollection("testCollection", SampleDoc.class);
    assertEquals("id", collection.insert(new SampleDoc("id", "name")));
    assertEquals(new SampleDoc("id", "name"), collection.find(new Query(Filter.eq("_id", "id"))));
  }

  private Config getMongoDataSourceConfig() {
    return ConfigFactory.parseMap(
        Map.of("type", "mongodb", "uri", this.connectionString, "databaseName", DB_NAME));
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class SampleDoc {
    private String _id;
    private String name;
  }
}
