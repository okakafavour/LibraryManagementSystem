package com.codewithfavour.service;



import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.model.LibraryMember;
import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.data.repository.LibraryMemberRepository;
import com.codewithfavour.dto.request.LoginLibraryMemberRequest;
import com.codewithfavour.dto.request.RegisterLibraryMemberRequest;
import com.codewithfavour.dto.response.BookResponse;
import com.codewithfavour.dto.response.LoginLibraryMemberResponse;
import com.codewithfavour.dto.response.RegisterLibraryMemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryMemberServiceImplTest {

    RegisterLibraryMemberRequest registerLibraryMemberRequest;
    LoginLibraryMemberRequest loginLibraryMemberRequest;

    @Autowired
    LibraryMemberRepository libraryMemberRepository;

    @Autowired
    LibraryMemberServiceImpl userService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryMemberService libraryMemberService;

    @BeforeEach
    void setUp() {
        registerLibraryMemberRequest = new RegisterLibraryMemberRequest();
        loginLibraryMemberRequest = new LoginLibraryMemberRequest();
        libraryMemberRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testToRegisterAUser(){
        registerRequest();
        RegisterLibraryMemberResponse registerResponse = userService.register(registerLibraryMemberRequest);
        assertEquals(1, libraryMemberRepository.count());
    }

    @Test
    public void testToRegisterAUser_AndLogin() {
        RegisterLibraryMemberRequest registerLibraryMemberRequest = new RegisterLibraryMemberRequest();
        registerLibraryMemberRequest.setFullName("Fave");
        registerLibraryMemberRequest.setEmail("fave@codewithfavour.com");
        registerLibraryMemberRequest.setPassword("123456");
        registerLibraryMemberRequest.setPhoneNumber("01234567890");

        RegisterLibraryMemberResponse registerResponse = userService.register(registerLibraryMemberRequest);
        assertEquals("Registered successfully", registerResponse.getMessage());

        LoginLibraryMemberRequest loginLibraryMemberRequest = new LoginLibraryMemberRequest();
        loginLibraryMemberRequest.setEmail("fave@codewithfavour.com");
        loginLibraryMemberRequest.setPassword("123456");

        LoginLibraryMemberResponse loginLibraryMemberResponse = userService.login(loginLibraryMemberRequest);
        assertEquals("Login successfully", loginLibraryMemberResponse.getMessage());
    }


    private void registerRequest(){
        registerLibraryMemberRequest.setFullName("Fave");
        registerLibraryMemberRequest.setEmail("fave@codewithfavour.com");
        registerLibraryMemberRequest.setPassword("123456");
        registerLibraryMemberRequest.setPhoneNumber("01234567890");
    }


    @Test
    public void testThatWhenUserLoginWithWrongPassword_throwAnError(){
        RegisterLibraryMemberRequest registerLibraryMemberRequest = new RegisterLibraryMemberRequest();
        registerLibraryMemberRequest.setFullName("Fave");
        registerLibraryMemberRequest.setEmail("fave@codewithfavour.com");
        registerLibraryMemberRequest.setPassword("123456");
        registerLibraryMemberRequest.setPhoneNumber("01234567890");

        RegisterLibraryMemberResponse registerResponse = userService.register(registerLibraryMemberRequest);
        assertEquals("Registered successfully", registerResponse.getMessage());

        LoginLibraryMemberRequest loginLibraryMemberRequest = new LoginLibraryMemberRequest();
        loginLibraryMemberRequest.setEmail("fave@codewithfavour.com");
        loginLibraryMemberRequest.setPassword("222222");

        LoginLibraryMemberResponse loginLibraryMemberResponse = userService.login(loginLibraryMemberRequest);
        assertEquals("Invalid credential", loginLibraryMemberResponse.getMessage());
    }

    @Test
    public void testThatUserCanBorrowBook(){
        LibraryMember member = new LibraryMember();
        member.setBorrowedBookIds(new ArrayList<>());
        member.setBorrowingHistory(new ArrayList<>());
        libraryMemberRepository.save(member);

        Book book = new Book();
        book.setTitle("Merlin");
        book.setAvailable(true);
       bookRepository.save(book);

       String result = libraryMemberService.borrowBook(member.getUserId(), book.getBookId());
       assertEquals("borrowed book successfully", result);

        Book updatedBook = bookRepository.findById(book.getBookId()).get();
       assertFalse(updatedBook.isAvailable());
    }

    @Test
    public void testThatUserReturnBookBorrowed() {
        LibraryMember member = new LibraryMember();
        member.setBorrowedBookIds(new ArrayList<>());
        member.setReturnedBookIds(new ArrayList<>());
        member.setBorrowingHistory(new ArrayList<>());

        libraryMemberRepository.save(member);

        Book book = new Book();
        book.setTitle("Merlin");
        book.setAvailable(false);
        bookRepository.save(book);

        member.getBorrowedBookIds().add(book.getBookId());
        member.getBorrowingHistory().add(book.getBookId());
        libraryMemberRepository.save(member);

        String result = libraryMemberService.returnBook(member.getUserId(), book.getBookId());

        assertEquals("returned book successfully", result);

        Book updatedBook = bookRepository.findById(book.getBookId()).get();
        assertTrue(updatedBook.isAvailable());
    }
    @Test
    public void testThatUserCanViewListOfBooks() {

        Book book1 = new Book();
        book1.setTitle("The Gorge");
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setTitle("AVATAR");
        book2.setAvailable(true);

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = libraryMemberService.viewBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
    }

    @Test
    public void testThatUserCanViewBorrowingHistory(){
            Book book1 = new Book();
            book1.setTitle("The Alchemist");
            book1.setAvailable(true);
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setTitle("1984");
            book2.setAvailable(true);
            bookRepository.save(book2);

            LibraryMember member = new LibraryMember();
            member.setBorrowingHistory(new ArrayList<>());
            member.getBorrowingHistory().add(book1.getBookId());
            member.getBorrowingHistory().add(book2.getBookId());
            libraryMemberRepository.save(member);

            List<String> borrowedHistory = libraryMemberService.viewBorrowedBookHistory(member.getUserId());

            assertNotNull(borrowedHistory);
            assertEquals(2, borrowedHistory.size());
            assertTrue(borrowedHistory.contains("The Alchemist"));
            assertTrue(borrowedHistory.contains("1984"));
        }

    }
