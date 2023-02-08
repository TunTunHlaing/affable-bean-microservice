package com.example.affablebeanui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ProductDto(int id, String name,
                         double price,
                         String description,
                         @JsonProperty("last_update") LocalDateTime lastUpdate,
                         String categoryName) {
}
