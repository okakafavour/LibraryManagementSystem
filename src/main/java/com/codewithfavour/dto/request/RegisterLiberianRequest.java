package com.codewithfavour.dto.request;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RegisterLiberianRequest {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

}
