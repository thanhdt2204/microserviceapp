package com.example.employeeservice.handler.query;

import com.example.commonservice.dto.EmployeeDTO;
import com.example.commonservice.queries.GetEmployeeQuery;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.queries.GetAllEmployeesQuery;
import com.example.employeeservice.repository.EmployeeRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeQueryHandler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeDTO handle(GetEmployeeQuery getBooksQuery) {
        EmployeeDTO dto = new EmployeeDTO();
        Employee employee = employeeRepository.getById(getBooksQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    @QueryHandler
    List<EmployeeDTO> handle(GetAllEmployeesQuery getAllEmployeesQuery) {
        return employeeRepository.findAll()
                .stream().map(employee -> new EmployeeDTO(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getKin(), employee.getIsDisciplined()))
                .collect(Collectors.toList());
    }

}
