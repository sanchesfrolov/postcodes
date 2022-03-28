package com.suburb.postcodes.request;

import com.suburb.postcodes.dto.PostcodeDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record CreatePostcodesRequest(
        @NotEmpty(message = "Postcode list must contain at least one postcode") List<@Valid PostcodeDTO> postcodes) {
}
