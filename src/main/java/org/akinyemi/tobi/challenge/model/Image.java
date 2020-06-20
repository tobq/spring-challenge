package org.akinyemi.tobi.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Image(
        @JsonProperty("path")String path,
        @JsonProperty("extension")String extension
) {
}
