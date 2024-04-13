package br.com.ifsp.tickets.infra.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginRequest(

        @JsonProperty("login") String login,
        @JsonProperty("password") String password
) {
}
