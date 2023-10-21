package com.example.borrowservice.controller.command;

import com.example.borrowservice.commands.CreateBorrowCommand;
import com.example.borrowservice.commands.DeleteBorrowCommand;
import com.example.borrowservice.commands.UpdateBorrowCommand;
import com.example.borrowservice.model.BorrowModel;
import com.example.borrowservice.service.BorrowService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public String borrowBook(@RequestBody BorrowModel model) {
        String id = UUID.randomUUID().toString();
        try {
            CreateBorrowCommand command = new CreateBorrowCommand(id, model.getBookId(), model.getEmployeeId(), new Date());
            commandGateway.sendAndWait(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Borrowed book successfully with id " + id;
    }

    @PutMapping
    public String returnBook(@RequestBody BorrowModel model) {
        String borrowId = borrowService.findBorrowId(model.getEmployeeId(), model.getBookId());
        UpdateBorrowCommand command = new UpdateBorrowCommand(borrowId, model.getBookId(), model.getEmployeeId(), new Date());
        commandGateway.sendAndWait(command);
        return "Returned book successfully with id " + borrowId;
    }

    @DeleteMapping("/{id}")
    public String deleteBorrow(@PathVariable String id) {
        DeleteBorrowCommand command = new DeleteBorrowCommand(id);
        commandGateway.sendAndWait(command);
        return "Deleted borrow successfully with id " + id;
    }

}
