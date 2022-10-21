package com.api.textsearch.controller;

import com.api.textsearch.dto.TextSearchRequest;
import com.api.textsearch.dto.TextSearchResponse;
import com.api.textsearch.service.impl.TextSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.api.textsearch.support.RestPath.API_VERSION_1_WORDS_OPTION;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TextSearchController
{
    private final TextSearchServiceImpl textSearchServiceImpl;
    @GetMapping(API_VERSION_1_WORDS_OPTION)
    public ResponseEntity<TextSearchResponse> getUserLocation(@RequestParam String similar, @RequestBody TextSearchRequest textSearchRequest) throws IOException
    {
        log.debug("REST request to get frequency and similarity of word :{} against text:{}", similar, textSearchRequest.getText());
        TextSearchResponse response = textSearchServiceImpl.analyzeText(textSearchRequest.getText(), similar);
        return ResponseEntity.ok().body(response);
    }
}
