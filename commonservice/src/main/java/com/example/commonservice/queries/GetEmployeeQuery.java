package com.example.commonservice.queries;

public class GetEmployeeQuery {

    private String employeeId;

    public GetEmployeeQuery(String employeeId) {
        super();
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}
