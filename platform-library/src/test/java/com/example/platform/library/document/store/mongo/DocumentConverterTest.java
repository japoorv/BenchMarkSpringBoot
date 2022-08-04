package com.example.platform.library.document.store.mongo;

import static junit.framework.TestCase.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentConverterTest {
  private DocumentConverter documentConverter;

  @BeforeEach
  void setUp() {
    this.documentConverter = new DocumentConverter();
  }

  @Test
  void testDTOToDocumentConversion() throws JsonProcessingException {
    assertEquals(
        from(Map.of("_id", "1", "name", "name")),
        documentConverter.convert(TestDTOClass.builder()._id("1").name("name").build()));
  }

  @Test
  void testDocumentToDTOConversion() throws JsonProcessingException {
    assertEquals(
        TestDTOClass.builder()._id("1").name("name").build(),
        documentConverter.convert(from(Map.of("_id", "1", "name", "name")), TestDTOClass.class));
  }

  private Document from(Map<String, String> keyVal) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return Document.parse(objectMapper.writeValueAsString(keyVal));
  }

  @Data
  @NoArgsConstructor
  @Builder
  @AllArgsConstructor
  private static class TestDTOClass {
    private String _id;
    private String name;
  }
}
