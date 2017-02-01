package com.roudforest;

import com.roudforest.service.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

    @Autowired
    private RunnerService runnerService;

	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
        runnerService.execute();
    }
}
