package com.roudforest.endpoint;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

@Service
public class WsTranslatorServiceFactory {

    private static final TimeUnit TIME_UNIT_SECONDS = TimeUnit.SECONDS;

    @Value("${ws.translator.url}")
    private String baseUrl;

    @Value("${ws.request.timeout}")
    private String requestTimeout;

    private OkHttpClient setHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(Integer.valueOf(requestTimeout), TIME_UNIT_SECONDS)
                .connectTimeout(Integer.valueOf(requestTimeout), TIME_UNIT_SECONDS)
                .build();
    }

    public WsTranslatorService createTranslatorService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(setHttpClient())
                .build();

        return retrofit.create(WsTranslatorService.class);
    }
}
