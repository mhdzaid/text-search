package com.api.textsearch.service;

import com.api.textsearch.dto.TextSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextSearchService
{

    public void analyzeText(TextSearchRequest textSearchRequest) throws IOException {
        Analyzer customAnalyzer = CustomAnalyzer.builder()
                .withTokenizer("whitespace").build();
        InMemoryLuceneIndex luceneIndex = new InMemoryLuceneIndex(new RAMDirectory(), customAnalyzer);
        luceneIndex.indexDocument(textSearchRequest.getText(), textSearchRequest.getText());
        long frequency =  luceneIndex.countTerms(textSearchRequest.getQueryWord(),"body");
        luceneIndex.fuzzy(textSearchRequest.getQueryWord(), "body", 1);
    }
}
