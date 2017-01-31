package com.roudforest;

import com.roudforest.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

    @Autowired
    private ReviewService reviewService;

	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
        List<String> users = reviewService.getMostActiveUsers();
        System.out.println(users.size());

        List<String> food = reviewService.getMostCommentedFood();
        System.out.println(food.size());

        List<String> words = reviewService.getMostUsedWords();
        System.out.println(words.size());
    }
}
