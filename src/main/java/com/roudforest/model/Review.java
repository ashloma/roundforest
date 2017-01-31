package com.roudforest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Review {

    @Id
    private long id;
    private String productId;
    private String userId;
    private String profileName;
    private String text;
}
