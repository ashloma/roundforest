package com.roudforest.reader;

import com.roudforest.model.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.roudforest.utils.ApplicationStringUtils.getNullIfEmpty;

@Component
public class ReviewDataCommand {

    private static final String SKIP_LINE = "Id";

    @Value("${reviews.file.location}")
    private String fileName;

    public Review parseReview(List<String> line) {
        if (SKIP_LINE.equals(line.get(0))) {
            return null;
        }
        return new Review(Integer.parseInt(line.get(0)),
                getNullIfEmpty(line.get(1)),
                getNullIfEmpty(line.get(2)),
                getNullIfEmpty(line.get(3)),
                getNullIfEmpty(line.get(9)));
    }

    public String getFilePath() {
        return fileName;
    }
}
