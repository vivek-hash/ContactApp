package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="emp_thymeleaftab")
public class Employee {

	@Id
	private Integer empId;
	private String empName;
	
	private String dept;
	private String email;
	
	private Double empSal;
	
	private Double hra;
	private Double da;
	
	
}
