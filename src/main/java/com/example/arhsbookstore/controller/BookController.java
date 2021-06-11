package com.example.arhsbookstore.controller;

import com.example.arhsbookstore.model.Book;
import com.example.arhsbookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private  @ResponseBody Book saveBook(@PathVariable long isbn, @Valid @RequestBody Book book) {
        book.setIsbn(isbn);
        return bookService.saveOrUpdate(book);
    }

    /**
     * add list of books
     * @param books
     * @return
     */
    @PutMapping("/books")
    private @ResponseBody List<Book> addBooks (@RequestBody
                                               @Validated(BookController.class)
                                               @NotEmpty List<@Valid Book> books){
        return bookService.addBooks(books);
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

    /**
     * Handle client message for exceptions thrown for addBooks() api call
     *
     * @param constraintViolationException
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationExceptions(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" " + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occured.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
