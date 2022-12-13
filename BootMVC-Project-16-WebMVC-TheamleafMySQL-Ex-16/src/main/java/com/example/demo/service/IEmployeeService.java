package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Employee;

public interface IEmployeeService {

	Integer saveEmployee(Employee e);
	List<Employee> getAllEmployee();
	void deleteEmpById(Integer id);
	Employee getOneEmployee(Integer id);
	void updateEmployee(Employee e);
	
	Page<Employee> getAllEmployees(Pageable pageable);
	
	boolean isEmployeeExitsByEmail(String email);
}
