package com.example.platform.library.document.store.mongo;

import com.example.platform.library.document.store.Collection;
import com.example.platform.library.document.store.Query;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
public class MongoCollection<T> implements Collection<T> {

  private final MongoDatabase mongoDatabase;
  private final String collectionName;
  private final com.mongodb.client.MongoCollection mongoCollection;
  private final QueryConverter queryConverter;
  private final DocumentConverter documentConverter;
  private final Class<T> clazz;

  public MongoCollection(MongoDatabase mongoDatabase, String collectionName, Class<T> clazz) {
    this.mongoDatabase = mongoDatabase;
    this.collectionName = collectionName;
    this.mongoCollection = mongoDatabase.getCollection(collectionName);
    this.queryConverter = new QueryConverter();
    this.documentConverter = new DocumentConverter();
    this.clazz = clazz;
  }

  @Override
  public T find(Query query) {
    try {
      return this.documentConverter.convert(
          (Document) this.mongoCollection.find(this.queryConverter.convert(query)).first(), clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<T> findAll(Query query) {
    MongoCursor<Document> mongoCursor =
        this.mongoCollection.find(this.queryConverter.convert(query)).cursor();
    List<T> results = new ArrayList<>();
    try {
      while (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        results.add(this.documentConverter.convert(document, clazz));
      }
      return results;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String insert(T document) {
    try {
      return this.mongoCollection
          .insertOne(this.documentConverter.convert(document))
          .getInsertedId()
          .asString()
          .getValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
