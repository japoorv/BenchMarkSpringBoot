package com.example.platform.library.document.store;

public interface DataSource {
  public <T> Collection<T> getCollection(String name, Class<T> clazz);
}
