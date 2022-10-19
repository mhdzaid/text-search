package com.api.textsearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class TextSearchController
{
    @GetMapping("/api/text")
    public ResponseEntity<Void> getUserLocation()
    {

        return ResponseEntity.ok().build();
    }
}
