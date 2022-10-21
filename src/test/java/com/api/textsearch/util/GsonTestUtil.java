package com.api.textsearch.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonTestUtil
{
    private GsonTestUtil()
    {
    }

    // Creates the json object which will manage the information received
    private static GsonBuilder GSON_BUILDER;
    public static Gson GSON;

    static
    {
        GSON_BUILDER = new GsonBuilder();
        GSON = GSON_BUILDER.setPrettyPrinting().create();
    }
}
