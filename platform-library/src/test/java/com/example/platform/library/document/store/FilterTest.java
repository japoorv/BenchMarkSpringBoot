package com.example.platform.library.document.store;

import static com.example.platform.library.document.store.Filter.Operator.AND;
import static com.example.platform.library.document.store.Filter.Operator.EQ;
import static com.example.platform.library.document.store.Filter.Operator.OR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class FilterTest {
  private static final String COLUMN_NAME = "columnName";
  private static final String VALUE = "value";

  @Test
  void testSimpleFilter() {
    Filter filter = getSimpleFilter();
    assertEquals(EQ, filter.getOperator());
    assertEquals(COLUMN_NAME, filter.getColumnName());
    assertEquals(VALUE, filter.getValue());
  }

  @Test
  void testAndFilter() {
    Filter filter = getSimpleFilter();
    Filter filter1 = getSimpleFilter("c2", "v2");
    Filter andFilter = Filter.and(List.of(filter, filter1));

    assertEquals(List.of(filter, filter1), andFilter.getFilters());
    assertEquals(AND, andFilter.getCombinationOperator());
    assertEquals(null, andFilter.getOperator());
    assertEquals(null, andFilter.getColumnName());
    assertEquals(null, andFilter.getValue());
  }

  @Test
  void testOrFilter() {
    Filter filter = getSimpleFilter();
    Filter filter1 = getSimpleFilter("c2", "v2");
    Filter andFilter = Filter.or(List.of(filter, filter1));

    assertEquals(List.of(filter, filter1), andFilter.getFilters());
    assertEquals(OR, andFilter.getCombinationOperator());
    assertEquals(null, andFilter.getOperator());
    assertEquals(null, andFilter.getColumnName());
    assertEquals(null, andFilter.getValue());
  }

  @Test
  void testComplexFilter() {
    Filter filter = getSimpleFilter();
    Filter filter1 = getSimpleFilter("c2", "v2");
    Filter filter2 = getSimpleFilter("c3", "v3");
    Filter andFilter = Filter.and(List.of(filter, filter1));
    Filter complexFilter = Filter.or(List.of(filter2, andFilter));

    assertEquals(List.of(filter2, andFilter), complexFilter.getFilters());
    assertEquals(OR, complexFilter.getCombinationOperator());
    assertEquals(null, complexFilter.getOperator());
    assertEquals(null, complexFilter.getColumnName());
    assertEquals(null, complexFilter.getValue());
  }

  private Filter getSimpleFilter() {
    return Filter.eq(COLUMN_NAME, VALUE);
  }

  private Filter getSimpleFilter(String column, String value) {
    return Filter.eq(column, value);
  }
}
