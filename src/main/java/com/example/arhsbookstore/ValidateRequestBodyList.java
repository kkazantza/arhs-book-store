package com.example.arhsbookstore;

import javax.validation.Valid;
import java.util.List;

/**
 * Generic Class which validates List Of Request Body
 * @param <Book>
 */
public class ValidateRequestBodyList<Book> {

    @Valid
    private List<Book> requestBody;

    public List<Book> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(List<Book> requestBody) {
        this.requestBody = requestBody;
    }

    public ValidateRequestBodyList(List<Book> requestBody) {
        this.requestBody = requestBody;
    }

    public ValidateRequestBodyList() { }
}
