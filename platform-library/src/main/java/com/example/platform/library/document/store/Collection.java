package com.example.platform.library.document.store;

import java.util.List;

public interface Collection<T> {
  public T find(Query query);

  public List<T> findAll(Query query);

  public String insert(T document);
}
