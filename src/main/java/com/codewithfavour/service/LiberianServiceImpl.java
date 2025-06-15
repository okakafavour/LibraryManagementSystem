package com.codewithfavour.service;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.model.BorrowingRecord;
import com.codewithfavour.data.model.Liberian;
import com.codewithfavour.data.model.LibraryMember;
import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.data.repository.LiberianRepository;
import com.codewithfavour.data.repository.LibraryMemberRepository;
import com.codewithfavour.dto.request.BookRequest;
import com.codewithfavour.dto.request.LoginLiberianRequest;
import com.codewithfavour.dto.request.RegisterLiberianRequest;
import com.codewithfavour.dto.response.LoginLiberianResponse;
import com.codewithfavour.dto.response.RegisterLiberianResponse;
import com.codewithfavour.exception.BookNotFoundException;
import com.codewithfavour.exception.InvalidBookIdException;
import com.codewithfavour.util.LiberianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codewithfavour.util.LiberianMapper.mapToLiberianRequest;
import static com.codewithfavour.util.LiberianMapper.mapToLiberianResponse;


@Service
public class LiberianServiceImpl implements LiberianService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private LibraryMemberRepository libraryMemberRepository;

    @Autowired
    LiberianMapper liberianMapper;
    @Autowired
    private LiberianRepository liberianRepository;


    @Override
    public RegisterLiberianResponse register(RegisterLiberianRequest registerLiberianRequest) {
        Liberian liberian = mapToLiberianRequest(registerLiberianRequest);
        Liberian savedLiberian = liberianRepository.save(liberian);
        return mapToLiberianResponse(savedLiberian);
    }

    @Override
    public LoginLiberianResponse login(LoginLiberianRequest loginLiberianRequest) {
        return liberianMapper.mapToLiberianLogin(loginLiberianRequest);
    }
    public String checkOutBook(String bookId) {
        Optional<Book> optionBook = bookRepository.findById(bookId);
        if(optionBook.isEmpty()) throw new InvalidBookIdException(" Book not Found");

        Book book = optionBook.get();
        book.setAvailable(false);
        bookRepository.save(book);
        return "Book check out successfully";
    }


    @Override
    public String checkInBook(String bookId) {
        Optional<Book> optionBook = bookRepository.findById(bookId);
        if(optionBook.isEmpty()) throw new InvalidBookIdException(" Book not Found");

        Book book = optionBook.get();
        book.setAvailable(true);
        bookRepository.save(book);
        return "Book check in successfully";
    }

    @Override
    public void addBook(BookRequest bookRequest) {
        bookService.createBook(bookRequest);
    }

    @Override
    public void removeBookById(String bookId) {
        bookService.deleteBook(bookId);
    }

    @Override
    public void removeBookByTitle(String title) {
        List<Book> booksWithTitle = bookRepository.findByTitle(title);
        if (booksWithTitle.isEmpty()) {
            throw new BookNotFoundException("No books found with title: " + title);
        }
        bookRepository.deleteAll(booksWithTitle);
    }
    @Override
    public List<BorrowingRecord> viewAllBorrowingHistory() {
        List<BorrowingRecord> records = new ArrayList<>();

        List<LibraryMember> members = libraryMemberRepository.findAll();

        for (LibraryMember member : members) {
            List<String> borrowedBookIds = member.getBorrowingHistory();
            if (borrowedBookIds == null) continue;

            List<String> returnedBookIds = member.getReturnedBookIds();
            List<String> returnedIds = returnedBookIds != null ? returnedBookIds : new ArrayList<>();


            for (String bookId : borrowedBookIds) {
                bookRepository.findById(bookId).ifPresent(book -> {

                    BorrowingRecord record = new BorrowingRecord();
                    record.setLibraryMemberName(member.getFullName());
                    record.setBookTitle(book.getTitle());
                    record.setStatus(returnedIds.contains(bookId) ? "Returned" : "Borrowed");
                    records.add(record);
                });
            }
        }

        return records;
    }

    @Override
    public List<Book> viewAllBooks() {
        return bookRepository.findAll();
    }
}
