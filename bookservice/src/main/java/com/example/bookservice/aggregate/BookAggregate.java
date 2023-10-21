package com.example.bookservice.aggregate;

import com.example.bookservice.event.BookCreatedEvent;
import com.example.bookservice.event.BookDeletedEvent;
import com.example.bookservice.commands.CreateBookCommand;
import com.example.bookservice.commands.DeleteBookCommand;
import com.example.bookservice.commands.UpdateBookCommand;
import com.example.bookservice.event.BookUpdatedEvent;
import com.example.commonservice.commands.RollBackStatusBookCommand;
import com.example.commonservice.commands.UpdateStatusBookCommand;
import com.example.commonservice.event.StatusBookRollBackEvent;
import com.example.commonservice.event.StatusBookUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {

    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate() {
    }

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand) {
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand) {
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.bookId = event.getBookId();
    }

    @CommandHandler
    public void handle(UpdateStatusBookCommand command) {
        StatusBookUpdatedEvent event = new StatusBookUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(StatusBookUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }

    @CommandHandler
    public void handle(RollBackStatusBookCommand command) {
        StatusBookRollBackEvent event = new StatusBookRollBackEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(StatusBookRollBackEvent event) {
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }

}
