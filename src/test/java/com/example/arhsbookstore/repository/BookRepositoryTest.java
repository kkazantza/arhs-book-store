package com.example.arhsbookstore.repository;

import com.example.arhsbookstore.model.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp(){
        List<Book> books = createBooks();
        bookRepository.saveAll(books);
    }

    @After
    public void tearDown(){
        bookRepository.deleteAll();
    }

    @Test
    public void testFindAll(){
        List<Book> allBooks = bookRepository.findAll();
        assertEquals(2, allBooks.size());
    }

    @Test
    public void testFindById(){
        Optional<Book> optBook = bookRepository.findById(1l);
        assertTrue(optBook.isPresent());
    }

    @Test
    public void testFindById_NotFound(){
        Optional<Book> optBook = bookRepository.findById(1000l);
        assertFalse(optBook.isPresent());
    }

    private List<Book> createBooks(){
        Book testBook1=new Book();
        testBook1.setIsbn(1l);
        testBook1.setName("Lord of the Rings");
        testBook1.setAuthor("J.R.R. Tolkien");
        testBook1.setPublisher("Random House USA Inc");

        Book testBook2=new Book();
        testBook2.setIsbn(2l);
        testBook2.setName("Mary Poppins");
        testBook2.setAuthor("P.L. Travers");
        testBook2.setPublisher("Thames and Hudson Ltd");

        List<Book> allBooks = Arrays.asList(testBook1,testBook2);
        return allBooks;
    }
}
