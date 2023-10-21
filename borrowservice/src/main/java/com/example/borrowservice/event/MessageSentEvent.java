package com.example.borrowservice.event;

public class MessageSentEvent {

    private String id;
    private String employeeId;
    private String message;

    public MessageSentEvent() {
    }

    public MessageSentEvent(String id, String employeeId, String message) {
        super();
        this.id = id;
        this.employeeId = employeeId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
