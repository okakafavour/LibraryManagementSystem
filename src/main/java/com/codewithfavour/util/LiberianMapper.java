package com.codewithfavour.util;

import com.codewithfavour.data.model.Liberian;
import com.codewithfavour.data.repository.LiberianRepository;
import com.codewithfavour.dto.request.LoginLiberianRequest;
import com.codewithfavour.dto.request.RegisterLiberianRequest;
import com.codewithfavour.dto.response.LoginLiberianResponse;
import com.codewithfavour.dto.response.RegisterLiberianResponse;
import com.codewithfavour.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LiberianMapper {

    @Autowired
    private LiberianRepository liberianRepository;

    public static Liberian mapToLiberianRequest(RegisterLiberianRequest registerLiberianRequest) {
        Liberian liberian = new Liberian();

        Validation.ValidateFullName(registerLiberianRequest.getFullName());
        Validation.ValidatePassword(registerLiberianRequest.getPassword());
        Validation.ValidatePhoneNumber(registerLiberianRequest.getPhoneNumber());
        Validation.ValidateEmail(registerLiberianRequest.getEmail());

        liberian.setFullName(registerLiberianRequest.getFullName());
        liberian.setEmail(registerLiberianRequest.getEmail());
        liberian.setPassword(registerLiberianRequest.getPassword());
        liberian.setPhoneNumber(registerLiberianRequest.getPhoneNumber());
        return liberian;
    }

    public static RegisterLiberianResponse mapToLiberianResponse(Liberian liberian) {
        RegisterLiberianResponse response = new RegisterLiberianResponse();
        response.setLiberianId(liberian.getId());
        response.setFullName(liberian.getFullName());
        response.setEmail(liberian.getEmail());
        response.setPhoneNumber(liberian.getPhoneNumber());
        response.setMessage("Registered successfully");
        return response;
    }

    public LoginLiberianResponse mapToLiberianLogin(LoginLiberianRequest request) {

        LoginLiberianResponse response = new LoginLiberianResponse();
        Liberian liberian = liberianRepository.findByEmail(request.getEmail());

        if (liberian == null || !Objects.equals(liberian.getPassword(), request.getPassword())) {
            response.setMessage("Invalid credentials");
            return response;
        }

        response.setMessage("Login successful");
        return response;
    }

}
