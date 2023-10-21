package com.example.borrowservice.repository;

import com.example.borrowservice.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, String> {

    List<Borrow> findByEmployeeIdAndReturnDateIsNull(String employeeId);

    Borrow findByEmployeeIdAndBookIdAndReturnDateIsNull(String employeeId, String bookId);

}
