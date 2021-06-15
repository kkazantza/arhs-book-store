package com.example.arhsbookstore.controller;

import com.example.arhsbookstore.model.Book;
import com.example.arhsbookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @WebMvcTest instantiates only the web layer rather the whole context
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService service;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void shouldFetchBookByIsbn() throws Exception {
        Book book=new Book();
        final long isbn=1l;
        book.setIsbn(isbn);
        book.setName("Lord of the Rings");
        book.setAuthor("J.R.R. Tolkien");
        book.setPublisher("Random House USA Inc");

        BDDMockito.given(service.getBookByIsbn(isbn)).willReturn(book);

        mvc.perform(get("/arhs-book-store/book/{isbn}", isbn))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(book.getName())))
            .andExpect(jsonPath("$.author", is(book.getAuthor())))
            .andExpect(jsonPath("$.publisher", is(book.getPublisher())));


//        MvcResult result = mvc.perform(get("/arhs-book-store/book/{isbn}", isbn)
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andDo(MockMvcResultHandlers.print()).andReturn();
//        String expected = "[{isbn:1, name:\"Lord of the Rings\", author:\"J.R.R. Tolkien\", publisher:\"Random House USA Inc\"}]";
//        JSONAssert.assertEquals(expected, result.getResponse()
//                .getContentAsString(), true);

    }

    @Test
    public void shouldCreateBook() throws Exception {
        Book book=new Book();
        final long isbn=1l;
        book.setIsbn(isbn);
        book.setName("Lord of the Rings");
        book.setAuthor("J.R.R. Tolkien");
        book.setPublisher("Random House USA Inc");

        BDDMockito.given(service.saveOrUpdate(book)).willReturn(book);

        mvc.perform(post("/arhs-book-store/book/{isbn}", isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())))
                .andExpect(jsonPath("$.publisher", is(book.getPublisher())));
    }

    @Test
    public void shouldReturn400WhenBookNameIsEmpty() throws Exception{
        Book book=new Book();
        final long isbn=1l;
        book.setIsbn(isbn);
        book.setName("");
        book.setAuthor("J.R.R. Tolkien");
        book.setPublisher("Random House USA Inc");

        mvc.perform(post("/arhs-book-store/book/{isbn}", isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name is mandatory")));
    }


    @Test
    public void shouldFetchAllBooks() throws Exception {
        List<Book> allBooks = createBooks();
        Mockito.when(service.getAllBooks()).thenReturn(allBooks);

        mvc.perform(get("/arhs-book-store/books")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
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
