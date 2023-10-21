package com.example.bookservice.controller.command;

import com.example.bookservice.commands.CreateBookCommand;
import com.example.bookservice.commands.DeleteBookCommand;
import com.example.bookservice.commands.UpdateBookCommand;
import com.example.bookservice.model.BookModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookModel model) {
        String bookId = UUID.randomUUID().toString();
        CreateBookCommand command = new CreateBookCommand(bookId, model.getName(), model.getAuthor(), Boolean.TRUE);
        commandGateway.sendAndWait(command);
        System.out.println("LLLLLLLLLLLLLLLLL" + command.getAuthor());
        return "Added book successfully with book id: " + bookId;
    }

    @PutMapping
    public String updateBook(@RequestBody BookModel model) {
        UpdateBookCommand command = new UpdateBookCommand(model.getBookId(), model.getName(), model.getAuthor(), model.getIsReady());
        commandGateway.sendAndWait(command);
        return "Updated book successfully with book id: " + model.getBookId();
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable String id) {
        DeleteBookCommand command = new DeleteBookCommand(id);
        commandGateway.sendAndWait(command);
        return "Deleted book successfully with book id: " + id;
    }

}
