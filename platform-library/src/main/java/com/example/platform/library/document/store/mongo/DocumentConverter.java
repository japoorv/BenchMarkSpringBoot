package com.example.platform.library.document.store.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

public class DocumentConverter {
  private ObjectMapper objectMapper;

  public DocumentConverter() {
    this.objectMapper = new ObjectMapper();
  }

  public <T> Document convert(T document) throws JsonProcessingException {
    return Document.parse(objectMapper.writeValueAsString(document));
  }

  public <T> T convert(Document document, Class<T> clazz) throws JsonProcessingException {
    if (document == null) return null;
    return objectMapper.readValue(document.toJson(), clazz);
  }
}
