package com.example.bookservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeleteBookCommand {

    @TargetAggregateIdentifier
    private String bookId;

    public DeleteBookCommand(String bookId) {
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
