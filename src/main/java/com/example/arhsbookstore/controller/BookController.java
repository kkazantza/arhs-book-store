package com.example.arhsbookstore.controller;

import com.example.arhsbookstore.model.Book;
import com.example.arhsbookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/arhs-book-store")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    private List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/book")
    private Book saveBook(Book book) {
        return bookService.saveOrUpdate(book);
    }


}
