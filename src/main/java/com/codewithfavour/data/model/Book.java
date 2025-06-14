package com.codewithfavour.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Book {
    @Id
    private String bookId;
    private String title;
    private String author;
    private String ISBN;
    private String publicationYear;
    private boolean available;


}
