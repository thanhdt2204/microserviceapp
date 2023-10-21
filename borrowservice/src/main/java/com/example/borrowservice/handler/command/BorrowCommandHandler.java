package com.example.borrowservice.handler.command;

import com.example.borrowservice.entity.Borrow;
import com.example.borrowservice.event.BorrowCreatedEvent;
import com.example.borrowservice.event.BorrowDeletedEvent;
import com.example.borrowservice.event.BorrowUpdatedEvent;
import com.example.borrowservice.event.MessageSentEvent;
import com.example.borrowservice.repository.BorrowRepository;
import com.example.borrowservice.service.BorrowService;
import com.example.commonservice.model.MessageModel;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowCommandHandler {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowService borrowService;

    @EventHandler
    public void on(BorrowCreatedEvent event) {
        Borrow model = new Borrow();
        BeanUtils.copyProperties(event, model);
        borrowRepository.save(model);
    }

    @EventHandler
    public void on(BorrowUpdatedEvent event) {
        Borrow model = borrowRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(event.getEmployeeId(), event.getBookId());
        model.setReturnDate(event.getReturnDate());
        borrowRepository.save(model);
    }

    @EventHandler
    public void on(BorrowDeletedEvent event) {
        if (borrowRepository.findById(event.getId()).isPresent()) {
            borrowRepository.deleteById(event.getId());
        }
    }

    @EventHandler
    public void on(MessageSentEvent event) throws Exception{
        MessageModel message = new MessageModel(event.getEmployeeId(), event.getMessage());
        borrowService.sendMessage(message.getMessage());
    }

}
