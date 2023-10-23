package com.example.borrowservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

public class CreateBorrowCommand {

    @TargetAggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowDate;

    public CreateBorrowCommand(String id, String bookId, String employeeId, Date borrowDate) {
        super();
        this.id = id;
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.borrowDate = borrowDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

}
