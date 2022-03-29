package com.suburb.postcodes.response;

import com.suburb.postcodes.dto.SuburbDTO;

import java.util.List;

public record GetSuburbsByPostcodeRangeResponse(int totalNumberOfCharactersOfAllNamesCombined,
                                                List<SuburbDTO> suburbs) {
}
