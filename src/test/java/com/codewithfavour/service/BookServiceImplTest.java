package com.codewithfavour.service;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.response.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookServiceImplTest  {

    BookRequest bookRequest;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookServiceImpl bookService;

    @BeforeEach
    void setUp(){
        bookRequest = new BookRequest();
        bookRepository.deleteAll();
    }


    @Test
    public void testToCreateABook(){
        BookRequest bookRequest = new BookRequest();

        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setAvailable(true);
        bookRequest.setISBN("ISBN");
        bookRequest.setPublisher("publisher");
        bookRequest.setPublicationYear("2021");


        BookResponse bookResponse = bookService.createBook(bookRequest);
        String newBook = bookResponse.getBookId();
        System.out.println(bookResponse);
        assertEquals(1, bookRepository.count());
    }

    @Test
    public void testToUpdateABook(){
        BookRequest bookRequest = new BookRequest();

        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setAvailable(true);
        bookRequest.setISBN("ISBN");
        bookRequest.setPublicationYear("2024");

        BookResponse bookResponse = bookService.createBook(bookRequest);
        String newBook = bookResponse.getBookId();
        System.out.println(bookResponse);
        assertEquals(1, bookRepository.count());


        BookRequest updatedBookRequest = new BookRequest();

        updatedBookRequest.setAuthor("updated author");
        updatedBookRequest.setTitle("updated title");
        updatedBookRequest.setAvailable(true);
        updatedBookRequest.setPublicationYear("2024");
        updatedBookRequest.setISBN("22-24-5");

        BookResponse response = bookService.updateBook(newBook, updatedBookRequest );

        Optional<Book> updatedBook = bookRepository.findById(newBook);
        assertNotNull(updatedBook);
        assertEquals("updated author", updatedBook.get().getAuthor());
        assertEquals("updated title", updatedBook.get().getTitle());
        System.out.println(updatedBook);
    }

    @Test
    public void testToCreateTwoBooks_DeleteOneBook(){
        BookRequest firstBookRequest = new BookRequest();

        firstBookRequest.setAuthor("author");
        firstBookRequest.setTitle("title");
        firstBookRequest.setAvailable(true);
        firstBookRequest.setISBN("ISBN");
        firstBookRequest.setPublisher("publisher");
        firstBookRequest.setPublicationYear("2024");
        BookResponse bookResponse = bookService.createBook(firstBookRequest);
        String firstBook = bookResponse.getBookId();

        BookRequest secondBookRequest = new BookRequest();
        secondBookRequest.setAuthor("Second author");
        secondBookRequest.setTitle("Second title");
        secondBookRequest.setAvailable(true);
        secondBookRequest.setISBN("22-20-5");
        secondBookRequest.setPublicationYear("2025");

        bookService.createBook(secondBookRequest);
        assertEquals(2, bookRepository.count());

        bookService.deleteBook(firstBook);
        assertEquals(1, bookRepository.count());
    }

    @Test
    public void testToCreateThreeBooks_AndFindAllBooks(){
        BookRequest firstBookRequest = new BookRequest();

        firstBookRequest.setAuthor("author");
        firstBookRequest.setTitle("title");
        firstBookRequest.setAvailable(true);
        firstBookRequest.setISBN("ISBN");
        firstBookRequest.setPublisher("publisher");
        firstBookRequest.setPublicationYear("2024");
        bookService.createBook(firstBookRequest);

        BookRequest secondBookRequest = new BookRequest();
        secondBookRequest.setAuthor("Second author");
        secondBookRequest.setTitle("Second title");
        secondBookRequest.setAvailable(true);
        secondBookRequest.setISBN("22-20-5");
        secondBookRequest.setPublicationYear("2025");
        bookService.createBook(secondBookRequest);

        BookRequest ThirdBookRequest = new BookRequest();
        ThirdBookRequest.setAuthor("third author");
        ThirdBookRequest.setTitle("third title");
        ThirdBookRequest.setAvailable(true);
        ThirdBookRequest.setISBN("22-20-5");
        ThirdBookRequest.setPublicationYear("2026");
        bookService.createBook(ThirdBookRequest);

        assertEquals(3, bookService.getAllBooks().size());
    }

}