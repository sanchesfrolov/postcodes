package com.suburb.postcodes.response;

import com.suburb.postcodes.dto.SuburbDTO;

import java.util.List;
import java.util.Set;

public record GetSuburbsByPostcodeRangeResponse(int totalNumberOfCharactersOfAllNamesCombined, List<SuburbDTO> suburbs) {
}
