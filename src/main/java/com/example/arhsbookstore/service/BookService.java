package com.example.arhsbookstore.service;

import com.example.arhsbookstore.model.Book;
import com.example.arhsbookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book saveOrUpdate(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookByIsbn(long isbn) {
        return bookRepository.findById(isbn).get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Book> addBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }
}
