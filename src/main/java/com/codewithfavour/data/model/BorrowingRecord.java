package com.codewithfavour.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class BorrowingRecord {
    private String libraryMemberName;
    private String bookTitle;
    private String status;
}
