package com.example.commonservice.queries;

public class GetBookQuery {

    private String bookId;

    public GetBookQuery(String bookId) {
        super();
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
