package com.example.arhsbookstore.controller;

import com.example.arhsbookstore.model.Book;
import com.example.arhsbookstore.ValidateRequestBodyList;
import com.example.arhsbookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/arhs-book-store")
public class BookController {

    @Autowired
    BookService bookService;

    /**
     * get all books
     * @return
     */
    @GetMapping("/books")
    private List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * returns a book by isbn
     * @return
     */
    @GetMapping("/book/{isbn}")
    private @ResponseBody Book getBook(@PathVariable long isbn){
        return bookService.getBookByIsbn(isbn);
    }

    /**
     * update details of a book by isbn
     * @param book
     * @return
     */
    @PostMapping("/book/{isbn}")
    @ResponseStatus(HttpStatus.CREATED)
    private  @ResponseBody Book saveBook(@PathVariable long isbn, @Valid @RequestBody Book book) {
        book.setIsbn(isbn);
        return bookService.saveOrUpdate(book);
    }

    /**
     * add list of books
     * @param books
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/books")
    private @ResponseBody List<Book> addBooks (@RequestBody
                                               @Valid
                                               @NotEmpty ValidateRequestBodyList<Book> books){
        List<Book> bookList=books.getRequestBody();
        return bookService.addBooks(bookList);
    }


    /**
     * Handle MethodArgumentNotValidException validation exceptions for client message
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
