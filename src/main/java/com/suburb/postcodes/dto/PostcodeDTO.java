package com.suburb.postcodes.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static com.suburb.postcodes.Constants.POST_CODE_ERROR_MESSAGE;
import static com.suburb.postcodes.Constants.POST_CODE_PATTERN;

public record PostcodeDTO(
        @Pattern(regexp = POST_CODE_PATTERN, message = POST_CODE_ERROR_MESSAGE) String code,
        @NotEmpty(message = "Suburb list must contain at least one suburb") Set<@Valid SuburbDTO> suburbs) {
}
