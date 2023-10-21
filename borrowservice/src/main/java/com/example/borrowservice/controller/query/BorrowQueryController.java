package com.example.borrowservice.controller.query;

import com.example.borrowservice.dto.BorrowDTO;
import com.example.borrowservice.queries.GetAllBorrowsQuery;
import com.example.borrowservice.queries.GetAllBorrowsByEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public List<BorrowDTO> getAllBorrowsByEmployee(@PathVariable String employeeId) {
        GetAllBorrowsByEmployeeQuery query = new GetAllBorrowsByEmployeeQuery();
        query.setEmployeeId(employeeId);
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BorrowDTO.class)).join();
    }

    @GetMapping
    public List<BorrowDTO> getAllBorrows() {
        return queryGateway.query(new GetAllBorrowsQuery(), ResponseTypes.multipleInstancesOf(BorrowDTO.class)).join();
    }

}
