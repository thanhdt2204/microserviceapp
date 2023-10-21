package com.example.borrowservice.aggregate;

import com.example.borrowservice.commands.CreateBorrowCommand;
import com.example.borrowservice.commands.DeleteBorrowCommand;
import com.example.borrowservice.commands.SendMessageCommand;
import com.example.borrowservice.commands.UpdateBorrowCommand;
import com.example.borrowservice.event.BorrowCreatedEvent;
import com.example.borrowservice.event.BorrowDeletedEvent;
import com.example.borrowservice.event.BorrowUpdatedEvent;
import com.example.borrowservice.event.MessageSentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class BorrowAggregate {

    @AggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowDate;
    private Date returnDate;
    private String message;

    public BorrowAggregate() {

    }

    @CommandHandler
    public BorrowAggregate(CreateBorrowCommand command) {
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreatedEvent event) {
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowDate = event.getBorrowDate();
    }

    @CommandHandler
    public void handle(UpdateBorrowCommand command) {
        BorrowUpdatedEvent event = new BorrowUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.returnDate = event.getReturnDate();
    }

    @CommandHandler
    public void handle(DeleteBorrowCommand command) {
        BorrowDeletedEvent event = new BorrowDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowDeletedEvent event) {
        this.id = event.getId();
    }

    @CommandHandler
    public void handle(SendMessageCommand command) {
        MessageSentEvent event = new MessageSentEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(MessageSentEvent event) {
        this.id = event.getId();
        this.message = event.getMessage();
        this.employeeId = event.getEmployeeId();
    }

}
