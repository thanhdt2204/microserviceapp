package com.example.bookservice.handler.query;

import com.example.bookservice.queries.GetAllBooksQuery;
import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.commonservice.dto.BookDTO;
import com.example.commonservice.queries.GetBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookQueryHandler {

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookDTO handle(GetBookQuery getBookQuery) {
        BookDTO dto = new BookDTO();
        Book book = bookRepository.getById(getBookQuery.getBookId());
        BeanUtils.copyProperties(book, dto);
        return dto;
    }

    @QueryHandler
    List<BookDTO> handle(GetAllBooksQuery getAllBooksQuery) {
        return bookRepository.findAll()
                .stream().map(book -> new BookDTO(book.getBookId(), book.getName(), book.getAuthor(), book.getIsReady()))
                .collect(Collectors.toList());
    }

}
