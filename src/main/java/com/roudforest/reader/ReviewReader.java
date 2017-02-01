package com.roudforest.reader;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import com.roudforest.model.Review;
import com.roudforest.repository.ReviewRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static com.roudforest.utils.ExceptionUtils.propagateCatchableException;

@Component
public class ReviewReader {

    private static final Log LOGGER = LogFactory.getLog(ReviewReader.class);

    @Autowired
    private ReviewDataExtractor reviewDataCommand;
    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void init() {
        truncateDatabase();
        String filePath = reviewDataCommand.getFilePath();
        InputStream in = propagateCatchableException(() -> new FileInputStream(filePath));

        Reader decoder = new InputStreamReader(in, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(decoder);
        try {
            processFile(reader);
            LOGGER.info("Writing data finished.");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void processFile(CSVReader reader) throws IOException {
        String[] nextLine;
        long start = System.currentTimeMillis();
        LOGGER.info("Start processing file");
        List<Review> batchList = Lists.newArrayList();
        while ((nextLine = reader.readNext()) != null) {
            Review review = reviewDataCommand.parseReview(Arrays.asList(nextLine));
            saveBatch(batchList, review);
        }
        if (!batchList.isEmpty()) {
            reviewRepository.save(batchList);
        }
        LOGGER.info("Processing file completed");
        System.out.println(System.currentTimeMillis() - start);
    }

    private void saveBatch(List<Review> batchList, Review review) {
        if (review != null) {
            batchList.add(review);
        }
        if (batchList.size() == 1000) {
            reviewRepository.save(batchList);
            batchList.clear();
        }
    }

    private void truncateDatabase() {
        LOGGER.info("Truncate database");
        reviewRepository.deleteAll();
    }
}
