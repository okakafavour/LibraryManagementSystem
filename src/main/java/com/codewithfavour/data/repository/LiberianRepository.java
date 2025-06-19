package com.codewithfavour.data.repository;

import com.codewithfavour.data.model.Liberian;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiberianRepository extends MongoRepository<Liberian, String> {
    Liberian findByEmail(String email);
}
