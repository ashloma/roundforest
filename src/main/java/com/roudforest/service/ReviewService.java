package com.roudforest.service;

import com.roudforest.model.ResultObject;
import com.roudforest.model.Review;
import com.roudforest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ReviewService {

    private static final String DB_NAME = "review";
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoOperations mongoOperations;

    protected List<String> getResult(String mapper) {
        MapReduceResults<ResultObject> results = mongoOperations.mapReduce(DB_NAME, "classpath:" + mapper,
                "classpath:reduce.js", ResultObject.class);

        return StreamSupport.stream(results.spliterator(), true)
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .limit(1000)
                .map(result -> result.getId())
                .collect(Collectors.toList());
    }

    public List<String> getMostActiveUsers() {
        return getResult("mapUser.js")
                .stream()
                .map(id -> mongoOperations.findOne(Query.query(Criteria.where("userId").is(id)), Review.class))
                .map(r -> r.getProfileName())
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    public List<String> getMostCommentedFood() {
        return getResult("mapProduct.js")
                .stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    public List<String> getMostUsedWords() {
        return getResult("mapWords.js")
                .stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }
}
