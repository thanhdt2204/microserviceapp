package com.example.borrowservice.handler.query;

import com.example.borrowservice.dto.BorrowDTO;
import com.example.borrowservice.entity.Borrow;
import com.example.borrowservice.queries.GetAllBorrowsByEmployeeQuery;
import com.example.borrowservice.queries.GetAllBorrowsQuery;
import com.example.borrowservice.repository.BorrowRepository;
import com.example.commonservice.dto.BookDTO;
import com.example.commonservice.dto.EmployeeDTO;
import com.example.commonservice.queries.GetBookQuery;
import com.example.commonservice.queries.GetEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowQueryHandler {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowDTO> handle(GetAllBorrowsByEmployeeQuery query) {
        return entityToDto(
                borrowRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId())
        );
    }

    @QueryHandler
    public List<BorrowDTO> handle(GetAllBorrowsQuery query) {
        return entityToDto(borrowRepository.findAll());
    }

    private List<BorrowDTO> entityToDto(List<Borrow> borrows) {
        List<BorrowDTO> list = new ArrayList<>();
        borrows.forEach(s -> {
            BorrowDTO model = new BorrowDTO();
            BeanUtils.copyProperties(s, model);
            model.setNameBook(queryGateway.query(new GetBookQuery(model.getBookId()),
                    ResponseTypes.instanceOf(BookDTO.class)).join().getName());
            EmployeeDTO employee = queryGateway.query(new GetEmployeeQuery(model.getEmployeeId()),
                    ResponseTypes.instanceOf(EmployeeDTO.class)).join();
            model.setNameEmployee(employee.getFirstName() + " " + employee.getLastName());
            list.add(model);
        });
        return list;
    }

}
