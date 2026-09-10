package com.example.catalog;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
class CatalogController {

  private final JdbcTemplate jdbcTemplate;

  CatalogController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @GetMapping("/items")
  List<CatalogItem> items() {
    return List.of(
        new CatalogItem(1, "Notebook", BigDecimal.valueOf(7.99)),
        new CatalogItem(2, "Mechanical Pencil", BigDecimal.valueOf(3.49)),
        new CatalogItem(3, "Desk Lamp", BigDecimal.valueOf(24.95))
    );
  }

  @GetMapping("/database")
  DatabaseStatus database() {
    String databaseName = jdbcTemplate.queryForObject("select current_database()", String.class);
    return new DatabaseStatus(databaseName);
  }

  record CatalogItem(long id, String name, BigDecimal price) {
  }

  record DatabaseStatus(String database) {
  }
}
