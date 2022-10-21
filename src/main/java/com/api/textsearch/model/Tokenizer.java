package com.api.textsearch.model;

public enum Tokenizer
{
    CLASSICAL("classic");

    private final String name;
    Tokenizer(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
