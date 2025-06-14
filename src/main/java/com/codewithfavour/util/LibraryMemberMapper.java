package com.codewithfavour.util;

import com.codewithfavour.data.model.LibraryMember;
import com.codewithfavour.data.repository.LibraryMemberRepository;
import com.codewithfavour.dto.request.LoginLibraryMemberRequest;
import com.codewithfavour.dto.request.RegisterLibraryMemberRequest;
import com.codewithfavour.dto.response.LoginLibraryMemberResponse;
import com.codewithfavour.dto.response.RegisterLibraryMemberResponse;
import com.codewithfavour.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryMemberMapper {

    @Autowired
    private LibraryMemberRepository libraryMemberRepository;

    public static LibraryMember mapToRequest(RegisterLibraryMemberRequest registerLibraryMemberRequest) {
        LibraryMember libraryMember = new LibraryMember();

        Validation.ValidateFullName(registerLibraryMemberRequest.getFullName());
        Validation.ValidatePassword(registerLibraryMemberRequest.getPassword());
        Validation.ValidatePhoneNumber(registerLibraryMemberRequest.getPhoneNumber());
        Validation.ValidateEmail(registerLibraryMemberRequest.getEmail());

        libraryMember.setFullName(registerLibraryMemberRequest.getFullName());
        libraryMember.setEmail(registerLibraryMemberRequest.getEmail());
        libraryMember.setPassword(registerLibraryMemberRequest.getPassword());
        libraryMember.setPhoneNumber(registerLibraryMemberRequest.getPhoneNumber());
        return libraryMember;
    }

    public static RegisterLibraryMemberResponse mapToResponse(LibraryMember libraryMember) {
        RegisterLibraryMemberResponse response = new RegisterLibraryMemberResponse();
        response.setFullName(libraryMember.getFullName());
        response.setEmail(libraryMember.getEmail());
        response.setPhoneNumber(libraryMember.getPhoneNumber());
        response.setUserId(libraryMember.getUserId());
        response.setMessage("Registered successfully");
        return response;
    }

    public LoginLibraryMemberResponse mapToLogin(LoginLibraryMemberRequest request) {
        LoginLibraryMemberResponse response = new LoginLibraryMemberResponse();
        LibraryMember libraryMember = libraryMemberRepository.findByEmail(request.getEmail());

        if (libraryMember == null || !request.getPassword().equals(libraryMember.getPassword())) {
            response.setMessage("Invalid credential");
            return response;
        }
        response.setMessage("Login successfully");
        return response;
    }

}
