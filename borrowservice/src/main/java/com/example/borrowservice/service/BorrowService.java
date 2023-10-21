package com.example.borrowservice.service;

import com.example.borrowservice.repository.BorrowRepository;
import com.example.commonservice.model.MessageModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.xml.transform.Source;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) throws  Exception{
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("thanhdoan", msg);
        System.out.println(future.get().toString());
    }

    public String findBorrowId(String employeeId, String bookId) {
        return repository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId).getId();
    }

}
