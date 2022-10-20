package com.api.textsearch.service.impl;

import com.api.textsearch.TextsearchApplicationTests;
import com.api.textsearch.dto.TextSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TextsearchApplicationTests.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TextSearchServiceImpl.class})
class TextSearchServiceServiceImplTest
{

    @Autowired
    TextSearchServiceImpl textSearchServiceImpl;

    @Test
    void is_analyzer_getting_similar_words_and_correct_frequency() throws IOException
    {
        String text = "Word Words Wor word";
        String word = "Word";
        TextSearchResponse textSearchResponse = textSearchServiceImpl.analyzeText(text, word);

        assertEquals(1, textSearchResponse.getWordFrequency());
        assertEquals(3, textSearchResponse.getSimilarWords().size());

        assertTrue(textSearchResponse.getSimilarWords().contains("word"));
        assertTrue(textSearchResponse.getSimilarWords().contains("Wor"));
        assertTrue(textSearchResponse.getSimilarWords().contains("Words"));
    }

    @Test
    void is_analyzer_getting_similar_words_when_comma_added() throws IOException
    {
        String text = "Word, Words, Wor word";
        String word = "Word";
        TextSearchResponse textSearchResponse = textSearchServiceImpl.analyzeText(text, word);

        assertEquals(1, textSearchResponse.getWordFrequency());
        assertEquals(3, textSearchResponse.getSimilarWords().size());

        assertTrue(textSearchResponse.getSimilarWords().contains("word"));
        assertTrue(textSearchResponse.getSimilarWords().contains("Wor"));
        assertTrue(textSearchResponse.getSimilarWords().contains("Words"));
    }

    @Test
    void is_analyzer_case_dependent() throws IOException
    {
        String text = "Word Word Word word";
        String word = "Word";
        TextSearchResponse textSearchResponse = textSearchServiceImpl.analyzeText(text, word);

        assertEquals(3, textSearchResponse.getWordFrequency());
        assertEquals(1, textSearchResponse.getSimilarWords().size());

        assertTrue(textSearchResponse.getSimilarWords().contains("word"));
    }
}