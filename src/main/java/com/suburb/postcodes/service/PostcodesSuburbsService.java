package com.suburb.postcodes.service;

import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.request.CreatePostcodesAndSuburbsRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;

public interface PostcodesSuburbsService {
    void createPostcodesAndSuburbs(CreatePostcodesAndSuburbsRequest suburbsRequest);
    GetSuburbsByPostcodeRangeResponse getSuburbsByPostcodeRange(SuburbsByPostcodeRangeRequest suburbsRequest);
}
