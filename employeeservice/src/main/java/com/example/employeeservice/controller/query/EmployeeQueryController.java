package com.example.employeeservice.controller.query;

import com.example.commonservice.dto.EmployeeDTO;
import com.example.commonservice.queries.GetEmployeeQuery;
import com.example.employeeservice.queries.GetAllEmployeesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeDetail(@PathVariable String employeeId) {
        GetEmployeeQuery getEmployeeQuery = new GetEmployeeQuery(employeeId);
        return queryGateway.query(getEmployeeQuery, ResponseTypes.instanceOf(EmployeeDTO.class)).join();
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        GetAllEmployeesQuery getAllEmployeesQuery = new GetAllEmployeesQuery();
        return queryGateway.query(getAllEmployeesQuery, ResponseTypes.multipleInstancesOf(EmployeeDTO.class)).join();
    }

}
