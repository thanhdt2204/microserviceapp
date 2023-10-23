package com.example.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateStatusBookCommand {

    @TargetAggregateIdentifier
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowId;

    public UpdateStatusBookCommand(String bookId, Boolean isReady, String employeeId, String borrowId) {
        super();
        this.bookId = bookId;
        this.isReady = isReady;
        this.employeeId = employeeId;
        this.borrowId = borrowId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

}
