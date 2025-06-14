package com.codewithfavour.dto.response;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class BookResponse {
    private String bookId;
    private String title;
    private String author;
    private String ISBN;
    private String publicationYear;
    private boolean Available;
}
