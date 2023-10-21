package com.example.bookservice.controller.query;

import com.example.bookservice.queries.GetAllBooksQuery;
import com.example.commonservice.dto.BookDTO;
import com.example.commonservice.queries.GetBookQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{bookId}")
    public BookDTO getBookDetail(@PathVariable String bookId) {
        GetBookQuery getBookQuery = new GetBookQuery(bookId);
        return queryGateway.query(getBookQuery, ResponseTypes.instanceOf(BookDTO.class)).join();
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        GetAllBooksQuery getAllBooksQuery = new GetAllBooksQuery();
        return queryGateway.query(getAllBooksQuery, ResponseTypes.multipleInstancesOf(BookDTO.class)).join();
    }

}
