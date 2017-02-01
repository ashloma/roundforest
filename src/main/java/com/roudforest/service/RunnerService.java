package com.roudforest.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RunnerService {

    private static final Log LOGGER = LogFactory.getLog(RunnerService.class);
    private static final int THREADS_COUNT = 3;
    private ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT);

    @Autowired
    private ReviewService reviewService;

    public void execute() {
        process(createFutureList());
        executor.shutdown();
    }

    protected List<Future<List<String>>> createFutureList() {
        List<Future<List<String>>> futures = new LinkedList<>();
        futures.add(createUsersTask());
        futures.add(createFoodsTask());
        futures.add(createWordsTask());
        return futures;
    }

    protected void process(List<Future<List<String>>> futures) {
        AtomicInteger index = new AtomicInteger(1);
        futures.forEach(future -> {
            try {
                List<String> result = future.get();

                System.out.println(index.getAndIncrement());
                result.forEach(System.out::println);
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error(e);
            }
        });
    }

    protected Future<List<String>> createUsersTask() {
        return executor.submit(() -> reviewService.getMostActiveUsers());
    }

    protected Future<List<String>> createFoodsTask() {
        return executor.submit(() -> reviewService.getMostCommentedFood());
    }

    protected Future<List<String>> createWordsTask() {
        return executor.submit(() -> reviewService.getMostUsedWords());
    }
}
