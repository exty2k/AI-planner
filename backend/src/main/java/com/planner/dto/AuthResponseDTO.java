package com.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String refreshToken;

    public AuthResponseDTO(AuthResponseDTO authResponseDTO) {

    };
}
