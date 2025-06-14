package com.codewithfavour.dto.response;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RegisterLibraryMemberResponse {
    @Id
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String message;

}
