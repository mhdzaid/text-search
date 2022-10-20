package com.api.textsearch.service.impl;

import com.api.textsearch.dto.TextSearchResponse;
import com.api.textsearch.lucene.service.InMemoryLuceneIndex;
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

    @Override
    public TextSearchResponse analyzeText(String text, String queryWord) throws IOException {
        Analyzer customAnalyzer = CustomAnalyzer.builder()
                .withTokenizer("classic")
                .build();
        InMemoryLuceneIndex luceneIndex = new InMemoryLuceneIndex(new ByteBuffersDirectory(), customAnalyzer);
        luceneIndex.indexDocument("body", text);

        Long wordFrequency =  luceneIndex.countTerms(queryWord,"body");
        List<String> similarWords = luceneIndex.similarWords(queryWord, "body", 1);

        luceneIndex.deleteDocument();
        return new TextSearchResponse(similarWords, wordFrequency);
    }
}
