package com.suburb.postcodes.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

public record PostcodeDTO(
        @Pattern(regexp = "^[0-9]{4}$", message = "Postcode must contain four digits") String code,
        @NotEmpty(message = "Suburb list must contain at least one suburb") Set<@Valid SuburbDTO> suburbs) {
}
