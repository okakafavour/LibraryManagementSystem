package com.codewithfavour.dto.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class LoginLibraryMemberRequest {
    private String email;
    private String password;
    private String message;

}
