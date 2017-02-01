package com.roudforest.batch;


import com.roudforest.dto.TranslatedText;
import com.roudforest.model.Review;
import com.roudforest.service.TranslatorService;
import org.springframework.batch.item.ItemProcessor;

public class TranslatorProcessor implements ItemProcessor<Review, TranslatedText> {

    private TranslatorService translatorService;

    public TranslatorProcessor(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @Override
    public TranslatedText process(Review item) throws Exception {
        return translatorService.translate(item);
    }
}
