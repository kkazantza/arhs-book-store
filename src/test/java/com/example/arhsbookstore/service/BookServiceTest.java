package com.example.arhsbookstore.service;

import com.example.arhsbookstore.model.Book;
import com.example.arhsbookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void getAllBooksTest(){
        // stub repository items
        Mockito.when(bookRepository.findAll()).thenReturn(createBooks());

        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(1, allBooks.get(0).getIsbn());
        assertEquals(2, allBooks.get(1).getIsbn());
    }

    @Test
    public void getBookByIsbnTest(){
        Book mockBook=new Book();
        mockBook.setIsbn(1l);
        mockBook.setName("Lord of the Rings");
        mockBook.setAuthor("J.R.R. Tolkien");
        mockBook.setPublisher("Random House USA Inc");
        Mockito.when(bookRepository.findById(1l)).thenReturn(Optional.of(mockBook));

        Book book = bookService.getBookByIsbn(1l);
        assertEquals(1l, book.getIsbn());
    }

    @Test(expected = NoSuchElementException.class)
    public void getBookNotFoundTest(){
        Mockito.when(bookRepository.findById(1l)).thenReturn(Optional.empty());

        Book book = bookService.getBookByIsbn(1l);
    }

    @Test
    public void saveOrUpdateTest(){
        Book mockBook=new Book();
        mockBook.setIsbn(1l);
        mockBook.setName("Lord of the Rings");
        mockBook.setAuthor("J.R.R. Tolkien");
        mockBook.setPublisher("Random House USA Inc");
        Mockito.when(bookRepository.save(mockBook)).thenReturn(mockBook);

        Book book = bookService.saveOrUpdate(mockBook);
        assertEquals(1l, book.getIsbn());
    }

    @Test
    public void addBooksTest(){
        // stub repository items
        List<Book> mockBooks = createBooks();
        Mockito.when(bookRepository.saveAll(mockBooks)).thenReturn(mockBooks);

        List<Book> allBooks = bookService.addBooks(mockBooks);
        assertEquals(1, allBooks.get(0).getIsbn());
        assertEquals(2, allBooks.get(1).getIsbn());
    }



    private List<Book> createBooks(){
        Book testBook1=new Book();
        testBook1.setIsbn(1);
        testBook1.setName("Lord of the Rings");
        testBook1.setAuthor("J.R.R. Tolkien");
        testBook1.setPublisher("Random House USA Inc");

        Book testBook2=new Book();
        testBook2.setIsbn(2);
        testBook2.setName("Mary Poppins");
        testBook2.setAuthor("P.L. Travers");
        testBook2.setPublisher("Thames and Hudson Ltd");

        List<Book> allBooks = Arrays.asList(testBook1,testBook2);
        return allBooks;
    }
}
