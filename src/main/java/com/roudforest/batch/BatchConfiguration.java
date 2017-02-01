package com.roudforest.batch;

import com.roudforest.dto.TranslatedText;
import com.roudforest.model.Review;
import com.roudforest.service.TranslatorService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.HashMap;

@Configuration
public class BatchConfiguration {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private TranslatorService translatorService;

    @Bean
    public MongoItemReader<Review> reader() {
        MongoItemReader<Review> reader = new MongoItemReader<>();
        reader.setTemplate(mongoOperations);
        reader.setQuery("{}");
        HashMap<String,Sort.Direction> sort = new HashMap<>();
        sort.put("sequence", Sort.Direction.ASC);
        reader.setSort(sort);
        reader.setCollection("review");
        reader.setTargetType(Review.class);
        return reader;
    }

    @Bean
    public TranslatorProcessor processor() {
        return new TranslatorProcessor(translatorService);
    }

    @Bean
    public MongoItemWriter<TranslatedText> writer() {
        MongoItemWriter<TranslatedText> writer = new MongoItemWriter<>();
        writer.setCollection("translated");
        writer.setTemplate(mongoOperations);
        writer.setDelete(true);
        return writer;
    }

    @Bean
    @Conditional(TranslationCondition.class)
    public Job importTranslationJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importTranslationJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("translation")
                .<Review, TranslatedText> chunk(50)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
