package com.codewithfavour.service;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.data.model.BorrowingRecord;
import com.codewithfavour.data.model.LibraryMember;
import com.codewithfavour.data.repository.BookRepository;
import com.codewithfavour.data.repository.BorrowingRecordRepository;
import com.codewithfavour.data.repository.LibraryMemberRepository;
import com.codewithfavour.dto.request.LoginLibraryMemberRequest;
import com.codewithfavour.dto.request.RegisterLibraryMemberRequest;
import com.codewithfavour.dto.response.BookResponse;
import com.codewithfavour.dto.response.LoginLibraryMemberResponse;
import com.codewithfavour.dto.response.RegisterLibraryMemberResponse;
import com.codewithfavour.exception.ResourceNotFoundException;
import com.codewithfavour.util.LibraryMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codewithfavour.util.LibraryMemberMapper.*;


@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    @Autowired
    private LibraryMemberRepository libraryMemberRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    LibraryMemberMapper libraryMemberMapper;
    @Autowired
    private BookRepository bookRepository;


    @Override
    public RegisterLibraryMemberResponse register(RegisterLibraryMemberRequest registerLibraryMemberRequest) {
        LibraryMember libraryMember = mapToRequest(registerLibraryMemberRequest);
        LibraryMember savedLibraryMember = libraryMemberRepository.save(libraryMember);
        return mapToResponse(savedLibraryMember);
    }

    @Override
    public LoginLibraryMemberResponse login(LoginLibraryMemberRequest loginLibraryMemberRequest) {
        return libraryMemberMapper.mapToLogin(loginLibraryMemberRequest);
    }

    @Override
    public String borrowBook(String libraryMemberId, String bookId) {
        Optional<LibraryMember> libraryMemberOptional = libraryMemberRepository.findById(libraryMemberId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (libraryMemberOptional.isEmpty()) return "library member not found";
        if (bookOptional.isEmpty()) return "book not found";

        LibraryMember libraryMember = libraryMemberOptional.get();
        Book book = bookOptional.get();

        book.setAvailable(false);
        libraryMember.getBorrowedBookIds().add(bookId);
        libraryMember.getBorrowingHistory().add(bookId);

        bookRepository.save(book);
        libraryMemberRepository.save(libraryMember);

        BorrowingRecord record = new BorrowingRecord();
        record.setLibraryMemberName(libraryMember.getFullName());
        record.setBookTitle(book.getTitle());
        record.setStatus("BORROWED");
        borrowingRecordRepository.save(record);

        return "borrowed book successfully";
    }

    @Override
    public String returnBook(String libraryMember, String bookId) {
        Optional<LibraryMember> optionalLibraryMember = libraryMemberRepository.findById(libraryMember);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if(optionalLibraryMember.isEmpty()) return "library member not found";
        if(optionalBook.isEmpty()) return "book not found";

        LibraryMember libraryMember1= optionalLibraryMember.get();
        Book book = optionalBook.get();

        book.setAvailable(true);
        libraryMember1.getBorrowedBookIds().remove(bookId);

        bookRepository.save(book);
        libraryMemberRepository.save(libraryMember1);

        return "returned book successfully";
    }

    @Override
    public List<String> viewBorrowedBookHistory(String libraryMemberId) {
        LibraryMember libraryMember = libraryMemberRepository.findById(libraryMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Library member with ID " + libraryMemberId + " not found"));

        List<String> history = new ArrayList<>();
        for (String bookId : libraryMember.getBorrowingHistory()) {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            String bookTitle = bookOptional.map(Book::getTitle)
                    .orElse("Unknown Book (ID: " + bookId + ")");
            history.add(bookTitle);
        }
        return history;
    }

    @Override
    public List<Book> viewBooks() {
        return bookRepository.findAll();
    }



}
