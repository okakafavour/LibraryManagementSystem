package com.codewithfavour.data.repository;

import com.codewithfavour.data.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    @Override
    Optional<Book> findById(String bookId);
    List<Book> findByTitle(String title);
    List<Book>findByAvailableFalse();
}
