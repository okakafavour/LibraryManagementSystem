package com.codewithfavour.controllers;

import com.codewithfavour.data.model.LibraryMember;
import com.codewithfavour.dto.request.LoginLibraryMemberRequest;
import com.codewithfavour.dto.request.RegisterLibraryMemberRequest;
import com.codewithfavour.dto.response.LoginLibraryMemberResponse;
import com.codewithfavour.dto.response.RegisterLibraryMemberResponse;
import com.codewithfavour.service.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libraryMember")
public class LibraryMemberController {

    @Autowired
    private LibraryMemberService libraryMemberService;


    @RequestMapping("/register")
    public ResponseEntity<RegisterLibraryMemberResponse> registerLibraryMember(@RequestBody RegisterLibraryMemberRequest request) {
        return ResponseEntity.ok(libraryMemberService.register(request));
    }

    @RequestMapping("/login")
    public ResponseEntity<LoginLibraryMemberResponse> loginLibraryMember(@RequestBody LoginLibraryMemberRequest request) {
        return ResponseEntity.ok(libraryMemberService.login(request));
    }

    @PostMapping("/{LibraryMemberId}/borrow/{bookId}")
    public String borrowBook(@PathVariable String LibraryMemberId, @PathVariable String bookId) {
        return libraryMemberService.borrowBook(LibraryMemberId, bookId);
    }

    @PostMapping ("/{LibraryMemberId}/return/{bookId}")
    public String returnBook(@PathVariable String LibraryMemberId, @PathVariable String bookId) {
        return libraryMemberService.returnBook(LibraryMemberId, bookId);
    }

    @GetMapping("/book/{libraryMemberId}")
    public ResponseEntity<LibraryMember> (@PathVariable String libraryMemberId) {}
}
