package com.example.employeeservice.handler.command;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.event.EmployeeCreatedEvent;
import com.example.employeeservice.event.EmployeeDeletedEvent;
import com.example.employeeservice.event.EmployeeUpdatedEvent;
import com.example.employeeservice.repository.EmployeeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCommandHandler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        Employee book = new Employee();
        BeanUtils.copyProperties(event, book);
        employeeRepository.save(book);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        Employee employee = employeeRepository.getById(event.getEmployeeId());
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDisciplined(event.getIsDisciplined());
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeDeletedEvent event) {
        employeeRepository.deleteById(event.getEmployeeId());
    }

}
