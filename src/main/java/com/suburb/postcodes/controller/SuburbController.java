package com.suburb.postcodes.controller;

import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.request.CreatePostcodesRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;
import com.suburb.postcodes.service.SuburbsService;
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
@RequestMapping("/suburb")
@RequiredArgsConstructor
public class SuburbController {

    private final SuburbsService suburbsService;

    @PostMapping
    @Operation(summary = "Add postcodes")
    @ApiResponse(responseCode = "400", description = "Input validation error")
    @ApiResponse(responseCode = "201", description = "List postcodes was added")
    public ResponseEntity<Void> addSuburbs(@Valid @RequestBody CreatePostcodesRequest suburbsRequest) {
        suburbsService.createSuburbs(suburbsRequest);
        log.info("Add suburb request");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Add postcodes")
    @ApiResponse(responseCode = "400", description = "Input validation error")
    @ApiResponse(responseCode = "200", description = "List postcodes by postcode range")
    public ResponseEntity<GetSuburbsByPostcodeRangeResponse> getSuburbsByPostcodesRange(@Valid @RequestBody SuburbsByPostcodeRangeRequest suburbsByPostcodeRangeRequest) {
        log.info("Get postcodes by postcodes from {} to {}", suburbsByPostcodeRangeRequest.from(), suburbsByPostcodeRangeRequest.to());
        return new ResponseEntity<>(suburbsService.getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest), HttpStatus.OK);
    }
}
