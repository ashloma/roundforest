package com.roudforest.service;

import com.roudforest.dto.TextToTranslate;
import com.roudforest.dto.TranslatedText;
import com.roudforest.endpoint.WsTranslatorService;
import com.roudforest.endpoint.WsTranslatorServiceFactory;
import com.roudforest.model.Review;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TranslatorService {

    private static final Log LOGGER = LogFactory.getLog(RunnerService.class);

    @Autowired
    private WsTranslatorServiceFactory translatorServiceFactory;

    @Value("${input.language}")
    private String inputLanguage;
    @Value("${output.language}")
    private String outputLanguage;

    public TranslatedText translate(Review review) {
        final WsTranslatorService translatorService = translatorServiceFactory.createTranslatorService();
        try {
            return translatorService.getTranslate(convertToTranslateObject(review))
                    .execute()
                    .body();
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
    }

    protected TextToTranslate convertToTranslateObject(Review review) {
        return new TextToTranslate(inputLanguage, outputLanguage, review.getText());
    }

}
