package com.codewithfavour.controllers;

import com.codewithfavour.data.model.Book;
import com.codewithfavour.dto.request.LoginLibraryMemberRequest;
import com.codewithfavour.dto.request.RegisterLibraryMemberRequest;
import com.codewithfavour.dto.response.LoginLibraryMemberResponse;
import com.codewithfavour.dto.response.RegisterLibraryMemberResponse;
import com.codewithfavour.service.LibraryMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/libraryMember")
@CrossOrigin(origins = "*")
@Tag(name = "Library member", description = "operations related to library member")
public class LibraryMemberController {

    @Autowired
    private LibraryMemberService libraryMemberService;

    @Operation(summary = "register a new library member")
    @PostMapping("/register")
    public ResponseEntity<RegisterLibraryMemberResponse> registerLibraryMember(@RequestBody RegisterLibraryMemberRequest request) {
        return ResponseEntity.ok(libraryMemberService.register(request));
    }

    @Operation(summary = "login in the app")
    @PostMapping("/login")
    public ResponseEntity<LoginLibraryMemberResponse> loginLibraryMember(@RequestBody LoginLibraryMemberRequest request) {
        return ResponseEntity.ok(libraryMemberService.login(request));
    }
    @PostMapping("/{libraryMemberId}/borrow/title/{title}")
    public ResponseEntity<String> borrowBook(@PathVariable("libraryMemberId") String libraryMemberId, @PathVariable("title") String title) {
        String response = libraryMemberService.borrowBook(libraryMemberId, title);

        if (response.equalsIgnoreCase("Library member not found")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (response.equalsIgnoreCase("Book not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (response.equalsIgnoreCase("Book is already borrowed")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else if (response.equalsIgnoreCase("Borrowed book successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }



    @PostMapping("/{LibraryMemberId}/return/title/{title}")
    public String returnBook(@PathVariable String LibraryMemberId, @PathVariable String title) {
        return libraryMemberService.returnBook(LibraryMemberId, title);
    }
    @GetMapping("/borrow-history/{libraryMemberId}")
    public List<String> viewBorrowedBookHistory(@PathVariable String libraryMemberId) {
        return libraryMemberService.viewBorrowedBookHistory(libraryMemberId);
    }

    @GetMapping("/books")
    public ResponseEntity<Book> viewBooks(){
        List<Book> books = libraryMemberService.viewBooks();
        return !books.isEmpty()
                ? new ResponseEntity<>(books.get(0), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
