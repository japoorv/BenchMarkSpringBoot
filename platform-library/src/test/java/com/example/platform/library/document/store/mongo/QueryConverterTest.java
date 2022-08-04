package com.example.platform.library.document.store.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.platform.library.document.store.Filter;
import com.example.platform.library.document.store.Query;
import com.mongodb.client.model.Filters;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueryConverterTest {
  public QueryConverter queryConverter;

  @BeforeEach
  void setUp() {
    this.queryConverter = new QueryConverter();
  }

  @Test
  void testSimpleFilter() {
    Filter eqFilter = getEqualFilter();
    Query query = new Query(eqFilter);
    assertEquals(Filters.eq("name", "value"), queryConverter.convert(query));
  }

  @Test
  void testAndFilter() {
    Filter andFilter = Filter.and(List.of(getEqualFilter(), getEqualFilter("name-1", "value-1")));
    assertEquals(
        Filters.and(Filters.eq("name", "value"), Filters.eq("name-1", "value-1")),
        queryConverter.convert(new Query(andFilter)));
  }

  @Test
  void testOrFilter() {
    Filter orFilter = Filter.or(List.of(getEqualFilter(), getEqualFilter("name-1", "value-1")));
    assertEquals(
        Filters.or(Filters.eq("name", "value"), Filters.eq("name-1", "value-1")),
        queryConverter.convert(new Query(orFilter)));
  }

  @Test
  void testComplexFilter() {
    Filter orFilter =
        Filter.and(
            List.of(
                getEqualFilter(),
                Filter.or(
                    List.of(
                        getEqualFilter("name-2", "value-2"),
                        getEqualFilter("name-1", "value-1")))));
    assertEquals(
        Filters.and(
            Filters.eq("name", "value"),
            Filters.or(Filters.eq("name-2", "value-2"), Filters.eq("name-1", "value-1"))),
        queryConverter.convert(new Query(orFilter)));
  }

  private Filter getEqualFilter() {
    return Filter.eq("name", "value");
  }

  private Filter getEqualFilter(String name, String value) {
    return Filter.eq(name, value);
  }
}
