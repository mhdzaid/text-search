package com.api.textsearch.controller;

import com.api.textsearch.dto.TextSearchRequest;
import com.api.textsearch.service.TextSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class TextSearchController
{
    private final TextSearchService textSearchService;
    @GetMapping("/api/text")
    public ResponseEntity<Void> getUserLocation(@RequestBody TextSearchRequest textSearchRequest) throws IOException {
        textSearchService.analyzeText(textSearchRequest);
        return ResponseEntity.ok().build();
    }
}
