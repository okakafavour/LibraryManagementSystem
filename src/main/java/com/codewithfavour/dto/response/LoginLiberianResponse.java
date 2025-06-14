package com.codewithfavour.dto.response;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Data
@Document
public class LoginLiberianResponse {
    private String message;
}
