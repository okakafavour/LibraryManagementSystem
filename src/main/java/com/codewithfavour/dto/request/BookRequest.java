package com.codewithfavour.dto.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class BookRequest {
    private String title;
    private String author;
    private String publisher;
    private String ISBN;
    private String publicationYear;
    private boolean Available;
    private String coverageImage;

}
