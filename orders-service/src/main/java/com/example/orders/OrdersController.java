package com.example.orders;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/orders")
class OrdersController {

  private final JdbcTemplate jdbcTemplate;
  private final RestClient catalogClient;

  OrdersController(
      JdbcTemplate jdbcTemplate,
      @Value("${catalog.service.url:http://localhost:8081}") String catalogServiceUrl
  ) {
    this.jdbcTemplate = jdbcTemplate;
    this.catalogClient = RestClient.builder()
        .baseUrl(catalogServiceUrl)
        .build();
  }

  @GetMapping
  List<OrderSummary> orders() {
    return List.of(
        new OrderSummary(1001, "Notebook", 2),
        new OrderSummary(1002, "Desk Lamp", 1)
    );
  }

  @GetMapping("/catalog-items")
  List<Map<String, Object>> catalogItems() {
    return catalogClient.get()
        .uri("/api/catalog/items")
        .retrieve()
        .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {
        });
  }

  @GetMapping("/database")
  DatabaseStatus database() {
    String databaseName = jdbcTemplate.queryForObject("select current_database()", String.class);
    return new DatabaseStatus(databaseName);
  }

  record OrderSummary(long id, String itemName, int quantity) {
  }

  record DatabaseStatus(String database) {
  }
}
