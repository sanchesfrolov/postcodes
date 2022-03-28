package com.suburb.postcodes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suburb.postcodes.controller.SuburbController;
import com.suburb.postcodes.dto.PostcodeDTO;
import com.suburb.postcodes.dto.SuburbDTO;
import com.suburb.postcodes.request.CreatePostcodesRequest;
import com.suburb.postcodes.request.SuburbsByPostcodeRangeRequest;
import com.suburb.postcodes.response.GetSuburbsByPostcodeRangeResponse;
import com.suburb.postcodes.service.SuburbsService;
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

@WebMvcTest(SuburbController.class)
class SuburbControllerTest {

    @MockBean
    private SuburbsService suburbsService;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void testWhenAddSuburbsThenReturnCreatedResponseCode() throws Exception {
        CreatePostcodesRequest createPostcodesRequest = createPostcodesRequest("Sub1", "0200");

        performPostRequest(createPostcodesRequest, status().isCreated());

        Mockito.verify(suburbsService, Mockito.times(1)).createSuburbs(createPostcodesRequest);
    }

    @Test
    void testWhenAddSuburbsWithWrongPostcodeThenReturnBadRequest() throws Exception {
        String wrongPostcode = "02001";
        CreatePostcodesRequest createPostcodesRequest = createPostcodesRequest("Sub1", wrongPostcode);

        performPostRequest(createPostcodesRequest, status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).createSuburbs(createPostcodesRequest);
    }

    @Test
    void testWhenAddSuburbsWithEmptySuburbThenReturnBadRequest() throws Exception {
        String wrongSuburbName = "";
        CreatePostcodesRequest createPostcodesRequest = createPostcodesRequest(wrongSuburbName, "0200");

        performPostRequest(createPostcodesRequest, status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).createSuburbs(createPostcodesRequest);
    }

    @Test
    void testWhenAddSuburbsWithEmptyPayload() throws Exception {
        performPostRequest("{}", status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).createSuburbs(Mockito.any(CreatePostcodesRequest.class));
    }

    @Test
    void testWhenGetSuburbsByPostcodesWithCorrectRangeThenReturnCorrectResponse() throws Exception {
        SuburbsByPostcodeRangeRequest suburbsByPostcodeRangeRequest = new SuburbsByPostcodeRangeRequest("0200", "1000");
        SuburbDTO sub1 = new SuburbDTO("Sub1");
        SuburbDTO sub2 = new SuburbDTO("Sub2");
        SuburbDTO sub3 = new SuburbDTO("Sub3");

        GetSuburbsByPostcodeRangeResponse getSuburbsByPostcodeRangeResponse = new GetSuburbsByPostcodeRangeResponse(12, List.of(sub1, sub2, sub3));

        Mockito.when(suburbsService.getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest)).thenReturn(getSuburbsByPostcodeRangeResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/suburb")
                        .content(asJsonString(suburbsByPostcodeRangeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(getSuburbsByPostcodeRangeResponse)))
                .andExpect(status().isOk());

        Mockito.verify(suburbsService, Mockito.times(1)).getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest);
    }

    @Test
    void testWhenGetSuburbsByPostcodesWithWrongRangeThenReturnBadRequest() throws Exception {
        String wrongPostcodeParameter = "020";
        SuburbsByPostcodeRangeRequest suburbsByPostcodeRangeRequest = new SuburbsByPostcodeRangeRequest(wrongPostcodeParameter, "1000");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/suburb")
                        .content(asJsonString(suburbsByPostcodeRangeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(suburbsService, Mockito.times(0)).getSuburbsByPostcodeRange(suburbsByPostcodeRangeRequest);
    }

    private void performPostRequest(Object createPostcodesRequest, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/suburb")
                        .content(asJsonString(createPostcodesRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }


    private CreatePostcodesRequest createPostcodesRequest(String suburbName, String postCode) {
        SuburbDTO suburbDTO1 = new SuburbDTO(suburbName);
        PostcodeDTO postcodeDTO1 = new PostcodeDTO(postCode, Set.of(suburbDTO1));
        return new CreatePostcodesRequest(List.of(postcodeDTO1));
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }
}
