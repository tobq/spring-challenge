package org.akinyemi.tobi.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Character(
        @JsonProperty("id")int id,
        @JsonProperty("name")String name,
        @JsonProperty("description")String description,
        @JsonProperty("thumbnail")Image thumbnail
) {
}
