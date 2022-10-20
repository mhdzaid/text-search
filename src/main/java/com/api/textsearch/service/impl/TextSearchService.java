package com.api.textsearch.service.impl;

import com.api.textsearch.dto.TextSearchRequest;
import com.api.textsearch.dto.TextSearchResponse;
import com.api.textsearch.lucene.service.InMemoryLuceneIndex;
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
public class TextSearchService
{

    public TextSearchResponse analyzeText(TextSearchRequest textSearchRequest, String queryWord) throws IOException {
        Analyzer customAnalyzer = CustomAnalyzer.builder()
                .withTokenizer("whitespace")
                .build();
        InMemoryLuceneIndex luceneIndex = new InMemoryLuceneIndex(new ByteBuffersDirectory(), customAnalyzer);
        luceneIndex.indexDocument("body", textSearchRequest.getText());
        long wordFrequency =  luceneIndex.countTerms(queryWord,"body");
        List<String> similarWords = luceneIndex.similarWords(queryWord, "body", 1);
        luceneIndex.deleteDocument();
        return new TextSearchResponse(similarWords, wordFrequency);
    }
}
