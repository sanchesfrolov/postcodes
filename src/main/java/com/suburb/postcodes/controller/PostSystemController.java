package com.suburb.postcodes.controller;

import com.suburb.postcodes.request.CreatePostcodesAndSuburbsRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;
import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.service.PostcodesSuburbsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/postcode")
@RequiredArgsConstructor
public class PostSystemController {

    private final PostcodesSuburbsService suburbsService;

    @PostMapping
    @Operation(summary = "Add postcodes and suburbs")
    @ApiResponse(responseCode = "400", description = "Input validation error")
    @ApiResponse(responseCode = "201", description = "List postcodes in conjunction with suburbs  was added")
    public ResponseEntity<Void> addPostcodesAndSuburbs(@Valid @RequestBody CreatePostcodesAndSuburbsRequest suburbsRequest) {
        suburbsService.createPostcodesAndSuburbs(suburbsRequest);
        log.debug("Add suburb request");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get suburbs by postcode range")
    @ApiResponse(responseCode = "400", description = "Input validation error")
    @ApiResponse(responseCode = "200", description = "List postcodes by postcode range")
    public ResponseEntity<GetSuburbsByPostcodeRangeResponse> getSuburbsByPostcodesRange(@Valid @RequestBody SuburbsByPostcodeRangeRequest suburbsByPostcodeRangeRequest) {
        log.debug("Get suburbs by postcodes from {} to {}", suburbsByPostcodeRangeRequest.from(), suburbsByPostcodeRangeRequest.to());
        return new ResponseEntity<>(suburbsService.getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest), HttpStatus.OK);
    }
}
