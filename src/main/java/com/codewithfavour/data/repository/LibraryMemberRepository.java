package com.codewithfavour.data.repository;

import com.codewithfavour.data.model.LibraryMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryMemberRepository extends MongoRepository<LibraryMember, String> {
    LibraryMember findByEmail(String email);
}
