package com.aleksej.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CardInfoDto(
        Long id,
        @NotBlank String number,
        @NotBlank String holder,
        @Future LocalDate expirationDate,
        Long userId
) {}