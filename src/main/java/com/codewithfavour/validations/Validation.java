package com.codewithfavour.validations;

import com.codewithfavour.exception.*;
import org.springframework.stereotype.Service;

@Service
public class Validation {


    public static void ValidateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) throw new InvalidFullNameException("Register request full name is null or empty");
        if (!fullName.matches("^[a-zA-Z\\s-']+$")) throw new InvalidFullNameException("Full name can only contain letters, spaces, hyphens, and apostrophes");

    }

    public static void ValidateEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new InvalidEmailException("Email is null or empty");
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) throw new InvalidEmailException("Email is invalid");

    }

    public static void ValidatePassword(String password) {
        if(password == null || password.trim().isEmpty())throw new InvalidPasswordException("Register request password is null");
    }

    public static void ValidatePhoneNumber(String phoneNumber) {
        if(phoneNumber == null)throw new InvalidPhoneNumberException("Register phone number is null");
        if (!phoneNumber.matches("^0\\d{10}$")) throw new InvalidPhoneNumberException("Invalid phone number");

    }

    public static void ValidateAddress(String address) {
        if(address == null || address.trim().isEmpty())throw new InvalidAddressException("Register Address is null");
        if(!address.matches("^[a-zA-Z\\s-']+$"))throw new InvalidAddressException("Register Address is invalid");
    }



}
