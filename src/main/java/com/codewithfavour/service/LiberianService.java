package com.codewithfavour.service;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.model.BorrowingRecord;
import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.request.LoginLiberianRequest;
import com.codewithfavour.dto.request.RegisterLiberianRequest;
import com.codewithfavour.dto.response.LoginLiberianResponse;
import com.codewithfavour.dto.response.RegisterLiberianResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LiberianService {
    RegisterLiberianResponse register(RegisterLiberianRequest registerLiberianRequest);
    LoginLiberianResponse login (LoginLiberianRequest loginLiberianRequest);
    String checkOutBook(String bookId);
    String checkInBook(String bookId);
    void addBook(BookRequest bookRequest);
    void removeBookById(String bookId);
    void removeBookByTitle(String title);
    List<BorrowingRecord> viewAllBorrowingHistory();
    List<Book> viewAllBooks();

}
