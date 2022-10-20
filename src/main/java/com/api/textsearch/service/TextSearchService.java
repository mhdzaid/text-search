package com.api.textsearch.service;

import com.api.textsearch.dto.TextSearchResponse;

import java.io.IOException;

public interface TextSearchService
{
    TextSearchResponse analyzeText(String text, String queryWord) throws IOException;
}

