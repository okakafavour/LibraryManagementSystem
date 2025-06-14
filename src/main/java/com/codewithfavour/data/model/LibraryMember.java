package com.codewithfavour.data.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class LibraryMember {
    @Id
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<String> borrowedBookIds;
    private List<String> returnedBookIds;
    private List<String> borrowingHistory;

}
