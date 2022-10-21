package com.api.textsearch.controller;

import com.api.textsearch.TextsearchApplicationTests;
import com.api.textsearch.dto.TextSearchRequest;
import com.api.textsearch.dto.TextSearchResponse;
import com.api.textsearch.service.impl.TextSearchServiceImpl;
import com.api.textsearch.util.GsonTestUtil;
import com.api.textsearch.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static com.api.textsearch.support.RestPath.API_VERSION_1_WORDS_OPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TextsearchApplicationTests.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TextSearchServiceImpl.class})
class TextSearchControllerTest {

    @Autowired
    private TextSearchServiceImpl textSearchService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this);

        TextSearchController textSearchController = new TextSearchController(textSearchService);
        ReflectionTestUtils.setField(textSearchController, "textSearchService", textSearchService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(textSearchController)
                .build();
    }

    @Test
    void testGetFrequencyAndSimilarWords() throws Exception
    {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setText("Word Words Wor word");

        MvcResult response = this.mockMvc.perform(get(API_VERSION_1_WORDS_OPTION)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .param("similar","Word")
                        .content(TestUtil.convertObjectToJsonBytes(textSearchRequest)))
                        .andExpect(status().isOk())
                        .andReturn();

        String body = response.getResponse().getContentAsString();
        TextSearchResponse textSearchResponse = GsonTestUtil.GSON.fromJson(body, TextSearchResponse.class);

        assertEquals(1, textSearchResponse.getWordFrequency());
        assertEquals(3, textSearchResponse.getSimilarWords().size());

        assertTrue(textSearchResponse.getSimilarWords().contains("word"));
        assertTrue(textSearchResponse.getSimilarWords().contains("Wor"));
        assertTrue(textSearchResponse.getSimilarWords().contains("Words"));
    }
}