package com.example.platform.library.document.store.mongo;

import com.example.platform.library.document.store.Filter;
import com.example.platform.library.document.store.Filter.Operator;
import com.example.platform.library.document.store.Query;
import com.mongodb.client.model.Filters;
import java.util.stream.Collectors;
import org.bson.conversions.Bson;

public class QueryConverter {
  public QueryConverter() {}

  public Bson convert(Query query) {
    return convertFilter(query.getFilter());
  }

  private Bson convertFilter(Filter filter) {
    if (filter.getCombinationOperator() != null) {
      return convertCombinationalFilter(filter);
    }
    switch (filter.getOperator()) {
      case EQ:
        return Filters.eq(filter.getColumnName(), filter.getValue());
      default:
        throw new RuntimeException(
            "Operator " + filter.getOperator() + "not suitable for simple filter.");
    }
  }

  private Bson convertCombinationalFilter(Filter filter) {
    Operator operator = filter.getCombinationOperator();
    switch (operator) {
      case AND:
        return Filters.and(
            filter.getFilters().stream().map(this::convertFilter).collect(Collectors.toList()));
      case OR:
        return Filters.or(
            filter.getFilters().stream().map(this::convertFilter).collect(Collectors.toList()));
      default:
        throw new RuntimeException(
            "Operator " + operator + "not suitable for combination filters.");
    }
  }
}
