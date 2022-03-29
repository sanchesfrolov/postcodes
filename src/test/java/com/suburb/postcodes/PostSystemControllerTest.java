package com.suburb.postcodes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suburb.postcodes.controller.PostSystemController;
import com.suburb.postcodes.dto.PostcodeDTO;
import com.suburb.postcodes.dto.SuburbDTO;
import com.suburb.postcodes.request.CreatePostcodesAndSuburbsRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;
import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.service.PostcodesSuburbsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostSystemController.class)
class PostSystemControllerTest {

    @MockBean
    private PostcodesSuburbsService suburbsService;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String SUBURB_URL = "/postcode";

    @Test
    void when_AddSuburbs_Then_Return_201_ResponseCode() throws Exception {
        CreatePostcodesAndSuburbsRequest createPostcodesRequest = createPostcodesRequest("Sub1", "0200");

        performPostRequest(createPostcodesRequest, status().isCreated());

        Mockito.verify(suburbsService, Mockito.times(1)).createPostcodesAndSuburbs(createPostcodesRequest);
    }

    @Test
    void when_AddSuburbsWithWrongPostcode_Then_Return_400_ResponseCode() throws Exception {
        String wrongPostcode = "02001";
        CreatePostcodesAndSuburbsRequest createPostcodesRequest = createPostcodesRequest("Sub1", wrongPostcode);

        performPostRequest(createPostcodesRequest, status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).createPostcodesAndSuburbs(createPostcodesRequest);
    }

    @Test
    void when_AddSuburbsWithEmptySuburbName_Then_Return_400_ResponseCode() throws Exception {
        String wrongSuburbName = "";
        CreatePostcodesAndSuburbsRequest createPostcodesRequest = createPostcodesRequest(wrongSuburbName, "0200");

        performPostRequest(createPostcodesRequest, status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).createPostcodesAndSuburbs(createPostcodesRequest);
    }

    @Test
    void when_AddSuburbsWithEmptyPayload_Then_Return_400_ResponseCode() throws Exception {
        performPostRequest("{}", status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).createPostcodesAndSuburbs(Mockito.any(CreatePostcodesAndSuburbsRequest.class));
    }

    @Test
    void when_GetSuburbsByPostcodesWithCorrectPostCodeRange_Then_Return_200_ResponseCode() throws Exception {
        SuburbsByPostcodeRangeRequest suburbsByPostcodeRangeRequest = new SuburbsByPostcodeRangeRequest("0200", "1000");
        SuburbDTO sub1 = new SuburbDTO("Sub1");
        SuburbDTO sub2 = new SuburbDTO("Sub2");
        SuburbDTO sub3 = new SuburbDTO("Sub3");

        GetSuburbsByPostcodeRangeResponse getSuburbsByPostcodeRangeResponse = new GetSuburbsByPostcodeRangeResponse(12, List.of(sub1, sub2, sub3));

        Mockito.when(suburbsService.getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest)).thenReturn(getSuburbsByPostcodeRangeResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(SUBURB_URL)
                        .content(asJsonString(suburbsByPostcodeRangeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(getSuburbsByPostcodeRangeResponse)))
                .andExpect(status().isOk());

        Mockito.verify(suburbsService, Mockito.times(1)).getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest);
    }

    @Test
    void when_GetSuburbsByPostcodesWithWrongPostCodeRange_Then_Return_400_ResponseCode() throws Exception {
        String wrongPostcodeParameter = "020";
        SuburbsByPostcodeRangeRequest suburbsByPostcodeRangeRequest = new SuburbsByPostcodeRangeRequest(wrongPostcodeParameter, "1000");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(SUBURB_URL)
                        .content(asJsonString(suburbsByPostcodeRangeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest);
    }

    private void performPostRequest(Object createPostcodesRequest, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(SUBURB_URL)
                        .content(asJsonString(createPostcodesRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    private CreatePostcodesAndSuburbsRequest createPostcodesRequest(String suburbName, String postCode) {
        SuburbDTO suburbDTO1 = new SuburbDTO(suburbName);
        PostcodeDTO postcodeDTO1 = new PostcodeDTO(postCode, Set.of(suburbDTO1));
        return new CreatePostcodesAndSuburbsRequest(List.of(postcodeDTO1));
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }
}
