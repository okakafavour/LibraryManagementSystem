package com.codewithfavour.dto.response;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class LoginLiberianResponse {
    private String message;
}
