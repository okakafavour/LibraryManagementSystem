package com.codewithfavour.util;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.response.BookResponse;

public class BookMapper {

    public static Book mapToBook(BookRequest request){
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setISBN(request.getISBN());
        book.setAuthor(request.getAuthor());
        book.setAvailable(true);
        book.setStatus("available");

        book.setPublicationYear(request.getPublicationYear());
        return book;
    }

    public static BookResponse mapToResponse(Book book){
       BookResponse bookResponse = new BookResponse();
       bookResponse.setBookId(book.getBookId());
       bookResponse.setTitle(book.getTitle());
       bookResponse.setISBN(book.getISBN());
       bookResponse.setAuthor(book.getAuthor());
       bookResponse.setAvailable(book.isAvailable());
       book.setStatus(book.getStatus());
       bookResponse.setPublicationYear(book.getPublicationYear());
       return bookResponse;
    }
}
