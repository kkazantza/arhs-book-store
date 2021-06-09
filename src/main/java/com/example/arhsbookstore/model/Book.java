package com.example.arhsbookstore.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {


    @Id
    private long isbn;
    @Column
    private String name;
    @Column
    private String author;
    @Column
    private String publisher;
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    @Column
    private int pages;

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
