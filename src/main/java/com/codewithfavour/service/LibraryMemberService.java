package com.codewithfavour.service;


import com.codewithfavour.data.model.Book;
import com.codewithfavour.dto.request.LoginLibraryMemberRequest;
import com.codewithfavour.dto.request.RegisterLibraryMemberRequest;
import com.codewithfavour.dto.response.LoginLibraryMemberResponse;
import com.codewithfavour.dto.response.RegisterLibraryMemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LibraryMemberService {
    RegisterLibraryMemberResponse register(RegisterLibraryMemberRequest registerLibraryMemberRequest);
    LoginLibraryMemberResponse login (LoginLibraryMemberRequest loginLibraryMemberRequest);
    String borrowBook(String libraryMemberId, String bookId);
    String returnBook(String libraryMemberId, String bookId);
    List<String> viewBorrowedBookHistory(String libraryMemberId);
    List<Book> viewBooks();
}
