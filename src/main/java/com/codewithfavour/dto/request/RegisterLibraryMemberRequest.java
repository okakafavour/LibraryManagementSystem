package com.codewithfavour.dto.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class RegisterLibraryMemberRequest {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
}
