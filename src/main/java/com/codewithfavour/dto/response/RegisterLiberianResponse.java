package com.codewithfavour.dto.response;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RegisterLiberianResponse {
    @Id
    private String liberianId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
}
