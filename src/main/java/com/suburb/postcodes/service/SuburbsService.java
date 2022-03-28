package com.suburb.postcodes.service;

import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.request.CreatePostcodesRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;

public interface SuburbsService {
    void createSuburbs(CreatePostcodesRequest suburbsRequest);
    GetSuburbsByPostcodeRangeResponse getSuburbsByPostcodeRange(SuburbsByPostcodeRangeRequest suburbsRequest);
}
