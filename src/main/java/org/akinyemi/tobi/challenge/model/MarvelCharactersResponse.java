package org.akinyemi.tobi.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MarvelCharactersResponse(
        @JsonProperty("code")int code,
        @JsonProperty("data")MarvalCharactersData data
) {
}
