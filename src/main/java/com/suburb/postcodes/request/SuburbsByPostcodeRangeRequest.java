package com.suburb.postcodes.request;

import javax.validation.constraints.Pattern;

public record SuburbsByPostcodeRangeRequest(
        @Pattern(regexp = "^[0-9]{4}$", message = "Postcode must contain four digits") String from,
        @Pattern(regexp = "^[0-9]{4}$", message = "Postcode must contain four digits") String to) {
}