package com.suburb.postcodes.service;

import com.suburb.postcodes.dto.PostcodeDTO;
import com.suburb.postcodes.dto.SuburbDTO;
import com.suburb.postcodes.entity.Postcode;
import com.suburb.postcodes.entity.Suburb;
import com.suburb.postcodes.repository.PostcodeRepository;
import com.suburb.postcodes.repository.SuburbRepository;
import com.suburb.postcodes.request.CreatePostcodesAndSuburbsRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;
import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostcodesSuburbsServiceImpl implements PostcodesSuburbsService {

    private final SuburbRepository suburbRepository;
    private final PostcodeRepository postcodeRepository;

    @Override
    @Transactional
    public void createPostcodesAndSuburbs(CreatePostcodesAndSuburbsRequest suburbsRequest) {
        addPostcodesIfDoesNotExist(suburbsRequest
                .postcodes()
                .stream()
                .map(PostcodeDTO::code)
                .collect(Collectors.toSet()));

        addSuburbsIfDoesNotExist(suburbsRequest
                .postcodes()
                .stream()
                .flatMap(postcode -> postcode.suburbs().stream())
                .collect(Collectors.toSet()));

        suburbsRequest.postcodes().forEach(this::linkSuburbsToPostcode);
    }

    @Override
    public GetSuburbsByPostcodeRangeResponse getSuburbsByPostcodeRange(SuburbsByPostcodeRangeRequest suburbsRequest) {
        List<SuburbDTO> suburbs = suburbRepository.findAllSuburbNamesByPostcodesRange(Short.parseShort(suburbsRequest.from()),
                        Short.parseShort(suburbsRequest.to()))
                .stream()
                .sorted()
                .map(SuburbDTO::new)
                .toList();
        return new GetSuburbsByPostcodeRangeResponse(suburbs.stream().map(SuburbDTO::suburbName).collect(Collectors.joining()).length(), suburbs);
    }

    private void linkSuburbsToPostcode(PostcodeDTO postcodeDTO) {
        Postcode postcode = postcodeRepository.findByCode(Short.parseShort(postcodeDTO.code()));
        Set<Suburb> suburbs = suburbRepository.findAllByNameIn(postcodeDTO.suburbs().stream().map(SuburbDTO::suburbName).collect(Collectors.toSet()));
        postcode.getSuburbs().addAll(suburbs);
        postcodeRepository.save(postcode);
    }


    private void addPostcodesIfDoesNotExist(Set<String> postcodes) {
        Set<Postcode> newPostcodes = postcodes.stream()
                .filter(Predicate.not(postcode -> postcodeRepository.existsByCode(Short.parseShort(postcode))))
                .map(postcode -> new Postcode(Short.parseShort(postcode))).collect(Collectors.toSet());
        if (!newPostcodes.isEmpty()) {
            postcodeRepository.saveAll(newPostcodes);
        }
    }

    private void addSuburbsIfDoesNotExist(Set<SuburbDTO> suburbs) {
        Set<Suburb> newSuburbs = suburbs.stream()
                .filter(Predicate.not(suburbDTO -> suburbRepository.existsByName(suburbDTO.suburbName())))
                .map(s -> new Suburb(s.suburbName())).collect(Collectors.toSet());
        if (!newSuburbs.isEmpty()) {
            suburbRepository.saveAll(newSuburbs);
        }
    }
}
