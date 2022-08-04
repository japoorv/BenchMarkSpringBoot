package com.example.platform.library.document.store.mongo;

import com.example.platform.library.document.store.Collection;
import com.example.platform.library.document.store.DataSource;
import com.example.platform.library.document.store.DataSourceConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.HashMap;
import java.util.Map;

public class MongoSyncDataSource implements DataSource {
  private final MongoDatabase mongoDatabase;
  private Map<String, Collection> mongoCollections;

  public MongoSyncDataSource(DataSourceConfig config) {
    ConnectionString connString = new ConnectionString(config.getUri());
    MongoClientSettings settings =
        MongoClientSettings.builder().applyConnectionString(connString).retryWrites(true).build();
    MongoClient mongoClient = MongoClients.create(settings);
    this.mongoDatabase = mongoClient.getDatabase(config.getDatabaseName());
    this.mongoCollections = new HashMap<>();
  }

  @Override
  public <T> Collection<T> getCollection(String name, Class<T> clazz) {
    return mongoCollections.computeIfAbsent(
        name, unused -> new MongoCollection(mongoDatabase, name, clazz));
  }
}
