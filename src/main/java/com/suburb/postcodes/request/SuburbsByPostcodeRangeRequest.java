package com.suburb.postcodes.request;

import javax.validation.constraints.Pattern;

import static com.suburb.postcodes.Constants.POST_CODE_ERROR_MESSAGE;
import static com.suburb.postcodes.Constants.POST_CODE_PATTERN;

public record SuburbsByPostcodeRangeRequest(
        @Pattern(regexp = POST_CODE_PATTERN, message = POST_CODE_ERROR_MESSAGE) String from,
        @Pattern(regexp = POST_CODE_PATTERN, message = POST_CODE_ERROR_MESSAGE) String to) {
}