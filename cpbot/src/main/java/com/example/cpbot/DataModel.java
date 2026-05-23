package com.example.cpbot;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataModel(
        @JsonProperty("event") String name,
        @JsonProperty("href") String url,
        @JsonProperty("start") String startTime,
        @JsonProperty("end") String endTime,
        @JsonProperty("host") String site
) {}