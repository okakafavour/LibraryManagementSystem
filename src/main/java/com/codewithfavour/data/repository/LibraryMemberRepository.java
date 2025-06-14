package com.codewithfavour.data.repository;

import com.codewithfavour.data.model.LibraryMember;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryMemberRepository extends MongoRepository<LibraryMember, String> {
    LibraryMember findByEmail(String email);
}
