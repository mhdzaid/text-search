package com.api.textsearch.service.impl;

import com.api.textsearch.dto.TextSearchResponse;
import com.api.textsearch.lucene.service.InMemoryLuceneIndex;
import com.api.textsearch.model.Tokenizer;
import com.api.textsearch.service.TextSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextSearchServiceImpl implements TextSearchService
{
    private static final String DEFAULT_FIELD = "body";
    /**
     * Returns word frequency and similar words
     * @param text
     * @param queryWord
     * @return
     * @throws IOException
     */
    @Override
    public TextSearchResponse analyzeText(String text, String queryWord) throws IOException
    {
        Analyzer customAnalyzer = CustomAnalyzer.builder()
                .withTokenizer(Tokenizer.CLASSICAL.getName())
                .build();
        InMemoryLuceneIndex luceneIndex = new InMemoryLuceneIndex(new ByteBuffersDirectory(), customAnalyzer);
        luceneIndex.indexDocument(DEFAULT_FIELD, text);

        Long wordFrequency =  luceneIndex.countTerms(queryWord,DEFAULT_FIELD);
        List<String> similarWords = luceneIndex.similarWords(queryWord, DEFAULT_FIELD, 1);

        luceneIndex.deleteDocument();
        return new TextSearchResponse(similarWords, wordFrequency);
    }
}
