package com.example.employeeservice.controller.command;

import com.example.employeeservice.commands.CreateEmployeeCommand;
import com.example.employeeservice.commands.DeleteEmployeeCommand;
import com.example.employeeservice.commands.UpdateEmployeeCommand;
import com.example.employeeservice.model.EmployeeModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeModel model) {
        String employeeId = UUID.randomUUID().toString();
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                employeeId, model.getFirstName(), model.getLastName(), model.getKin(), false
        );
        commandGateway.sendAndWait(command);
        return "Added employee successfully with employee id: " + employeeId;
    }

    @PutMapping
    public String updateEmployee(@RequestBody EmployeeModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(
                model.getEmployeeId(), model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined()
        );
        commandGateway.sendAndWait(command);
        return "Updated employee successfully with employee id: " + model.getEmployeeId();
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable String id) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(id);
        commandGateway.sendAndWait(command);
        return "Deleted employee successfully with employee id: " + id;
    }

}
