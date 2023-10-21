package com.example.borrowservice.handler.command;

import com.example.borrowservice.commands.DeleteBorrowCommand;
import com.example.borrowservice.commands.SendMessageCommand;
import com.example.borrowservice.event.BorrowCreatedEvent;
import com.example.borrowservice.event.BorrowDeletedEvent;
import com.example.commonservice.commands.RollBackStatusBookCommand;
import com.example.commonservice.commands.UpdateStatusBookCommand;
import com.example.commonservice.dto.BookDTO;
import com.example.commonservice.dto.EmployeeDTO;
import com.example.commonservice.event.StatusBookRollBackEvent;
import com.example.commonservice.event.StatusBookUpdatedEvent;
import com.example.commonservice.queries.GetBookQuery;
import com.example.commonservice.queries.GetEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowSagaHandler {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event) {
        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());
            GetBookQuery getBookQuery = new GetBookQuery(event.getBookId());

            BookDTO bookDTO = queryGateway.query(getBookQuery, ResponseTypes.instanceOf(BookDTO.class)).join();
            if (bookDTO.getIsReady()) {
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false,
                        event.getEmployeeId(), event.getId());
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("Someone has already borrowed the book");
            }
        } catch (Exception e) {
            rollBackBorrowRecord(event.getId());
            System.out.println(e.getMessage());
        }
    }

    private void rollBackBorrowRecord(String id) {
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(StatusBookUpdatedEvent event) {
        try {
            GetEmployeeQuery getEmployeeQuery = new GetEmployeeQuery(event.getEmployeeId());
            EmployeeDTO employeeDTO = queryGateway.query(getEmployeeQuery, ResponseTypes.instanceOf(EmployeeDTO.class)).join();
            if (employeeDTO.getIsDisciplined()) {
                throw new Exception("The employee has been disciplined");
            } else {
                SendMessageCommand sendMessageCommand = new SendMessageCommand(event.getBorrowId(),
                        event.getEmployeeId(), "successfully borrowed the book !");
                commandGateway.sendAndWait(sendMessageCommand);
                SagaLifecycle.end();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rollBackStatusBook(event.getBookId(), event.getEmployeeId(), event.getBorrowId());
        }
    }

    private void rollBackStatusBook(String bookId, String employeeId, String borrowId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId, true, employeeId, borrowId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    public void handleRollBackBookStatus(StatusBookRollBackEvent event) {
        rollBackBorrowRecord(event.getBorrowId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(BorrowDeletedEvent event) {
        SagaLifecycle.end();
    }

}
