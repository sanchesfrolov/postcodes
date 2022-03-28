package com.suburb.postcodes;

import com.suburb.postcodes.dto.PostcodeDTO;
import com.suburb.postcodes.dto.SuburbDTO;
import com.suburb.postcodes.entity.Postcode;
import com.suburb.postcodes.entity.Suburb;
import com.suburb.postcodes.repository.PostcodeRepository;
import com.suburb.postcodes.repository.SuburbRepository;
import com.suburb.postcodes.request.CreatePostcodesRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;
import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.service.SuburbsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class SuburbsServiceImplTest {

    @Mock
    SuburbRepository suburbRepository;

    @Mock
    PostcodeRepository postcodeRepository;

    @InjectMocks
    SuburbsServiceImpl service;

    @Test
    void whenCreateSuburbsThenCallRespectiveRepositoriesMethods() {

        Short postCode0200 = Short.parseShort("0200");
        Short postCode0201 = Short.parseShort("0201");

        Mockito.when(postcodeRepository.existsByCode(postCode0200)).thenReturn(false);
        Mockito.when(postcodeRepository.existsByCode(postCode0201)).thenReturn(false);

        Mockito.when(suburbRepository.existsByName("Sub1")).thenReturn(false);
        Mockito.when(suburbRepository.existsByName("Sub2")).thenReturn(false);
        Mockito.when(suburbRepository.existsByName("Sub3")).thenReturn(false);
        Mockito.when(suburbRepository.existsByName("Sub4")).thenReturn(false);

        Mockito.when(postcodeRepository.findByCode(postCode0200)).thenReturn(new Postcode(postCode0200));
        Mockito.when(postcodeRepository.findByCode(postCode0201)).thenReturn(new Postcode(postCode0201));

        Mockito.when(suburbRepository.findAllByNameIn(Set.of("Sub1", "Sub2"))).thenReturn(Set.of(new Suburb("Sub1"), new Suburb("Sub2")));
        Mockito.when(suburbRepository.findAllByNameIn(Set.of("Sub3", "Sub4"))).thenReturn(Set.of(new Suburb("Sub3"), new Suburb("Sub4")));


        CreatePostcodesRequest createPostcodesRequest = createPostcodesRequest();
        service.createSuburbs(createPostcodesRequest);

        Set<Postcode> newPostcodes = Set.of(new Postcode(postCode0200), new Postcode(postCode0201));
        Set<Suburb> newSuburbs = Set.of(new Suburb("Sub1"), new Suburb("Sub2"), new Suburb("Sub3"), new Suburb("Sub4"));

        Mockito.verify(postcodeRepository, Mockito.times(1)).saveAll(newPostcodes);
        Mockito.verify(suburbRepository, Mockito.times(1)).saveAll(newSuburbs);
    }

    @Test
    void whenGetSuburbsByPostcodeRangeThenCallRespectiveRepositoriesMethods() {
        SuburbsByPostcodeRangeRequest suburbsRequest = new SuburbsByPostcodeRangeRequest("0200", "1000");
        Mockito.when(suburbRepository.findAllSuburbNamesByPostcodesRange(Short.parseShort(suburbsRequest.from()),
                Short.parseShort(suburbsRequest.to()))).thenReturn(Set.of("Sub2", "Sub3", "Sub1"));

        GetSuburbsByPostcodeRangeResponse result = service.getSuburbsByPostcodeRange(suburbsRequest);
        List<SuburbDTO> expectedSuburbs = List.of(new SuburbDTO("Sub1"), new SuburbDTO("Sub2"), new SuburbDTO("Sub3"));

        Assertions.assertEquals(12, result.totalNumberOfCharactersOfAllNamesCombined());
        Assertions.assertIterableEquals(expectedSuburbs, result.suburbs());

        Mockito.verify(suburbRepository, Mockito.times(1)).findAllSuburbNamesByPostcodesRange(Short.parseShort(suburbsRequest.from()),
                Short.parseShort(suburbsRequest.to()));
    }

    private CreatePostcodesRequest createPostcodesRequest() {
        SuburbDTO suburbDTO1 = new SuburbDTO("Sub1");
        SuburbDTO suburbDTO2 = new SuburbDTO("Sub2");
        SuburbDTO suburbDTO3 = new SuburbDTO("Sub3");
        SuburbDTO suburbDTO4 = new SuburbDTO("Sub4");
        PostcodeDTO postcodeDTO1 = new PostcodeDTO("0200", Set.of(suburbDTO1, suburbDTO2));
        PostcodeDTO postcodeDTO2 = new PostcodeDTO("0201", Set.of(suburbDTO3, suburbDTO4));
        return new CreatePostcodesRequest(List.of(postcodeDTO1, postcodeDTO2));
    }
}
