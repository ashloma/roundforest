package com.roudforest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextToTranslate {

    @JsonProperty("input_lang")
    private String inputLang;
    @JsonProperty("output_lang")
    private String outputLang;
    private String text;
}
