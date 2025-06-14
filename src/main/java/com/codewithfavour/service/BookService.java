package com.codewithfavour.service;

import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.response.BookResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
  BookResponse createBook(BookRequest bookRequest);
  BookResponse getBookId(String bookId);
  BookResponse updateBook(String bookId, BookRequest request);
   void deleteBook(String bookId);
   List<BookResponse> getAllBooks();
}
