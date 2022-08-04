package com.example.platform.library.document.store;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Builder(access = AccessLevel.PACKAGE)
@Data
public class Filter {

  // either combination of filters or a simple filter

  // combination filter
  private List<Filter> filters;
  private Operator combinationOperator;

  // simple filter
  private Operator operator;
  private String columnName;
  private String value;

  public enum Operator {
    AND,
    OR,
    EQ;
  }

  public static Filter and(List<Filter> filters) {
    return Filter.builder().combinationOperator(Operator.AND).filters(filters).build();
  }

  public static Filter or(List<Filter> filters) {
    return Filter.builder().combinationOperator(Operator.OR).filters(filters).build();
  }

  public static Filter eq(String columnName, String value) {
    return Filter.builder().operator(Operator.EQ).columnName(columnName).value(value).build();
  }
}
