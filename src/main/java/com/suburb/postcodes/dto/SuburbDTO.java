package com.suburb.postcodes.dto;

import javax.validation.constraints.Size;

public record SuburbDTO(
        @Size(min = 1, max = 200, message = "Suburb name size must be between 1 and 200 characters") String suburbName) {
}
