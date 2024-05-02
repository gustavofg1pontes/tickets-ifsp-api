package br.com.ifsp.tickets.infra.user.recovery.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecoveryRequest(
        @JsonProperty("token") String token,
        @JsonProperty("password") String password
) {
}
