package org.akinyemi.tobi.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MarvalCharactersData(
        @JsonProperty("offset")int offset,
        @JsonProperty("limit")int limit,
        @JsonProperty("total")int total,
        @JsonProperty("count")int count,
        @JsonProperty("results")List<Character>results
) {
}
