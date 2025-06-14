package com.codewithfavour.service;

import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.data.repository.LiberianRepository;
import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.request.LoginLiberianRequest;
import com.codewithfavour.dto.request.RegisterLiberianRequest;
import com.codewithfavour.dto.response.LoginLiberianResponse;
import com.codewithfavour.dto.response.RegisterLiberianResponse;
import com.codewithfavour.data.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LiberianServiceImplTest {

    @Autowired
    LiberianServiceImpl liberianService;
    RegisterLiberianRequest registerLiberianRequest;
    @Autowired
    private LiberianRepository liberianRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        registerLiberianRequest = new RegisterLiberianRequest();
        liberianRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testToRegisterAsALiberian() {
        RegisterLiberianRequest registerLiberianRequest = new RegisterLiberianRequest();
        registerLiberianRequest.setFullName("Fave");
        registerLiberianRequest.setEmail("fave@codewithfavour.com");
        registerLiberianRequest.setPassword("123456");
        registerLiberianRequest.setPhoneNumber("01234567890");

        RegisterLiberianResponse registerResponse = liberianService.register(registerLiberianRequest);
        assertEquals("Registered successfully", registerResponse.getMessage());
    }


    @Test
    public void toRegisterAsALiberian_AndLogin() {
        RegisterLiberianRequest registerLiberianRequest = new RegisterLiberianRequest();
        registerLiberianRequest.setFullName("Fave");
        registerLiberianRequest.setEmail("fave@codewithfavour.com");
        registerLiberianRequest.setPassword("123456");
        registerLiberianRequest.setPhoneNumber("01234567890");

        RegisterLiberianResponse registerResponse = liberianService.register(registerLiberianRequest);
        assertEquals("Registered successfully", registerResponse.getMessage());

        LoginLiberianRequest loginLiberianRequest = new LoginLiberianRequest();
        loginLiberianRequest.setEmail("fave@codewithfavour.com");
        loginLiberianRequest.setPassword("123456");

        LoginLiberianResponse loginLiberianResponse = liberianService.login(loginLiberianRequest);
        assertEquals("Login successfully", loginLiberianResponse.getMessage());

    }

    @Test
    public void testToCheckOutBook(){
        Book book = new Book();
        book.setBookId("1");
        book.setTitle("clean code");
        book.setAuthor("Fave");
        book.setISBN("23-21-01");
        book.setPublicationYear("2005");
        book.setAvailable(true);
        bookRepository.save(book);

        liberianService.checkOutBook("1");
        Optional<Book> updateBook = bookRepository.findById("1");
        assertFalse(updateBook.get().isAvailable());
    }

    @Test
    public void testToCheckInBook(){
        Book book = new Book();
        book.setBookId("2");
        book.setAuthor("sam");
        book.setTitle("Good code");
        book.setISBN("230-2222-121");
        book.setPublicationYear("2005");
        book.setAvailable(false);
        bookRepository.save(book);

        liberianService.checkInBook("2");
        Optional<Book> updateBook = bookRepository.findById("2");
        assertTrue(updateBook.get().isAvailable());
    }
    @Test
    public void testToAddABookToTheListOfBooks_AndfindAll() {
        BookRequest book1 = new BookRequest();
        book1.setTitle("My Mother");
        book1.setISBN("12345543-45");
        book1.setAuthor("james smith");
        book1.setPublicationYear("2005");
        book1.setAvailable(true);

        BookRequest book2 = new BookRequest();
        book2.setTitle("The Brain behind all");
        book2.setAuthor("vevlin");
        book2.setISBN("23456-49-45");
        book2.setPublicationYear("2004");
        book2.setAvailable(true);

        liberianService.addBook(book1);
        liberianService.addBook(book2);

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }


    @Test
    public void testToCreateABook_AndRemoveBookById(){
        Book book = new Book();
        book.setBookId("1");
        book.setTitle("clean code");
        book.setAuthor("Fave");
        book.setISBN("23-21-01");
        book.setPublicationYear("2005");
        book.setAvailable(true);
        bookRepository.save(book);

        liberianService.removeBookById("1");
        assertEquals(0, bookRepository.findAll().size());
    }

    @Test
    public void testToCreateABook_AndRemoveByTitle(){
        Book book = new Book();
        book.setBookId("1");
        book.setTitle("clean code");
        book.setAuthor("Fave");
        book.setISBN("23-21-01");
        book.setPublicationYear("2005");
        book.setAvailable(true);
        bookRepository.save(book);

         liberianService.removeBookByTitle("clean code");
        assertEquals(0, bookRepository.findAll().size());

    }
}