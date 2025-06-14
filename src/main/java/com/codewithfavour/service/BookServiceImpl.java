package com.codewithfavour.service;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.response.BookResponse;
import com.codewithfavour.exception.InvalidBookIdException;
import com.codewithfavour.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookResponse createBook(BookRequest request) {
        Book book = BookMapper.mapToBook(request);
        Book savedBook = bookRepository.save(book);
        return BookMapper.mapToResponse(savedBook);
    }

    @Override
    public BookResponse getBookId(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        
        if(optionalBook.isEmpty()) throw new InvalidBookIdException("Book not found");
        return BookMapper.mapToResponse(optionalBook.get());
    }

    @Override
    public BookResponse updateBook(String bookId, BookRequest request) {
        Book book = bookRepository.findById(bookId).get();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setISBN(request.getISBN());
        book.setPublicationYear(request.getPublicationYear());
        Book updatedBook = bookRepository.save(book);
        return BookMapper.mapToResponse(updatedBook);
    }

    @Override
    public void deleteBook(String bookId) {
        if(!bookRepository.existsById(bookId)) throw new InvalidBookIdException("Book not found");
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List <Book> books = bookRepository.findAll();
        List<BookResponse> responses = new ArrayList<>();

        for(Book book : books){
            BookResponse bookResponse = BookMapper.mapToResponse(book);
            responses.add(bookResponse);
        }
        return responses;
    }
}
