package com.codewithfavour.controllers;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.model.BorrowingRecord;
import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.dto.request.LoginLiberianRequest;
import com.codewithfavour.dto.response.LoginLiberianResponse;
import com.codewithfavour.service.BookService;
import com.codewithfavour.service.LiberianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/liberian")
public class LiberianController {

    @Autowired
    private LiberianService liberianService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginLiberianRequest request) {
        try {
            LoginLiberianResponse response = liberianService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login: " + e.getMessage());
        }
    }

    @PostMapping("/books/checkout/{bookId}")
    public ResponseEntity<String> checkoutBook(@PathVariable String bookId) {
        try {
            String message = liberianService.checkOutBook(bookId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    @PostMapping("/books/checkin/{bookId}")
    public ResponseEntity<String> checkInBook (@PathVariable String bookId){
        try {
            String message = liberianService.checkInBook(bookId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:63343")
    @PostMapping("/add-books")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.ok(savedBook);
    }


    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> removeById(@PathVariable String bookId) {
        try {
            liberianService.removeBookById(bookId);
            return ResponseEntity.ok("Book removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid book ID: " + e.getMessage());
        }
    }


    @DeleteMapping("/book/remove/title/{title}")
    public ResponseEntity<String> removeByTitle(@PathVariable String title){
        try {
            liberianService.removeBookByTitle(title);
            return ResponseEntity.ok("Book removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid book :" + e.getMessage());
        }
    }

    @GetMapping("/borrowing-history")
    public ResponseEntity<List<BorrowingRecord>> ViewAllBorrowingHistory(){
        List<BorrowingRecord> record = liberianService.viewAllBorrowingHistory();
        return new ResponseEntity<>(record, HttpStatus.OK);
    }


    @GetMapping("/view-books")
    public ResponseEntity<List<Book>> viewAllBooks() {
        List<Book> allBooks = liberianService.viewAllBooks();
        return allBooks.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

}

