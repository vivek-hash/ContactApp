package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Employee;
import com.example.demo.service.IEmployeeService;
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService service;
	
	@GetMapping("/register")
	public String showReg(Model model)
	{
		Employee employee = new Employee();
		// Form Backing Object
		model.addAttribute("employee" ,employee);
		
		return "EmpRegister";
	}	
	@PostMapping("/save")
	public String saveEmp(
				@ModelAttribute Employee employee ,Model model) {
		 Integer id = service.saveEmployee(employee);
		 
		 
		 model.addAttribute("message" ,"Employee '"+id+"' saved");
	
		 model.addAttribute("employee" , new Employee());
		 
		 return "EmpRegister";
	}
	
	/*
	 @GetMapping("/all")
	public String getAllEmployees(Model model) {
		
		 List<Employee> list = service.getAllEmployee();
	
		 model.addAttribute("list" ,list);
		return "EmpData";
	 }
	*/
	
	 @GetMapping("/all")
		public String getAllEmployees(@PageableDefault(page=0 ,size = 3) Pageable  pageable,Model model) {
			
			 Page<Employee> page = service.getAllEmployees(pageable);
		
			 model.addAttribute("list" , page.getContent());
			 model.addAttribute("page"  ,page);
			return "EmpData";
		
	 }
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam Integer empId) {

		service.deleteEmpById(empId);
		
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String showEditPAge(
			@RequestParam Integer empId ,Model model) {
		//read the request param from edit page and get the respected id object and send back to edit page
		Employee employee = service.getOneEmployee(empId);
		model.addAttribute("employee" ,employee);
		
		return "EditEmployee";
	}
	
	@PostMapping("/update")
	public String updateEmployee(@ModelAttribute Employee employee) {
		
		service.updateEmployee(employee);
		
		return "redirect:all";
	}
	
	// ---------AJAX CALL METHOD--------
	
	@GetMapping("/checkEmail")
	public  @ResponseBody String validateEmail(@RequestParam String email) {

		String message="";
		
		if(service.isEmployeeExitsByEmail(email)) {
		
			message = email + " Already Exist";
		}
		System.out.println(message);
		return message;
	}
	
}
