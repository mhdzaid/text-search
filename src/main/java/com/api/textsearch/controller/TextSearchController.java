package com.api.textsearch.controller;

import com.api.textsearch.dto.TextSearchRequest;
import com.api.textsearch.dto.TextSearchResponse;
import com.api.textsearch.service.impl.TextSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class TextSearchController
{
    private final TextSearchService textSearchService;
    @GetMapping("/api/words")
    public ResponseEntity<TextSearchResponse> getUserLocation(@RequestParam String similar, @RequestBody TextSearchRequest textSearchRequest) throws IOException {
        TextSearchResponse response = textSearchService.analyzeText(textSearchRequest, similar);
        return ResponseEntity.ok().body(response);
    }
}
