package com.roudforest.endpoint;

import com.roudforest.dto.TextToTranslate;
import com.roudforest.dto.TranslatedText;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WsTranslatorService {

    // provide your google credentials in format "header key: value"
    @Headers({"Content-Type: application/json" /*, ""*/})
    @POST("translate")
    Call<TranslatedText> getTranslate(@Body TextToTranslate toTranslate);
}
