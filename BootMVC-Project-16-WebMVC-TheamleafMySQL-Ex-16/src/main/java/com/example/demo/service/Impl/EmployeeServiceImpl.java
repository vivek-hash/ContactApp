package com.example.demo.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.IEmployeeService;

@Service //calculations, logics,TxManagement
public class EmployeeServiceImpl  implements IEmployeeService{

	@Autowired
	private EmployeeRepository repo;

	@Override
	public Integer saveEmployee(Employee e) {
		
		//calculation
		double esal =e.getEmpSal();
		double hra =esal*20/100.0;
		double da =esal*10/100.0;
		e.setHra(hra);
		e.setDa(da);
		
		// save employee
		e =repo.save(e);
		
		return e.getEmpId();
	}

	@Override
	public List<Employee> getAllEmployee() {
	
		List<Employee> list =repo.findAll();
		
		return list;
	}

	@Override
	public void deleteEmpById(Integer id) {
	
		/* Standard Code
		Employee e = repo.findById(id).orElseThrow(
				()->new EmployeeNotFoundException("Employee '"+ id +"' Not Exists"));
		
		// delete object
		
		repo.delete(e);
		
		*/
		//or else use below method getOneEmployee(Integer id)
		
		Employee e = getOneEmployee(id);
		
		repo.delete(e);
		
		/*
		
		if(repo.existsById(id)) {
			repo.deleteById(id);
		}else {
			throw new EmployeeNotFoundException("Employee '"+ id +"' Not Exists");
		}
		
		*/
		
		
		//repo.deleteById(id);
		
	}

	@Override
	public Employee getOneEmployee(Integer id) {
		
		Employee e = repo.findById(id).orElseThrow(
										()->new EmployeeNotFoundException("Employee '"+ id +"' Not Exists"));
		
		return e;
	
		/*
		  Optional<Employee> opt = repo.findById(id);
		  	if(opt.isPresent()) {
		
			Employee e = opt.get();
			return e;
		}
		else {
			throw new EmployeeNotFoundException("Employee '"+ id +"' Not Exists");
		 }
		*/	
		
		//else return null
		//	return null;
	}

	@Override
	public void updateEmployee(Employee e) {
	
		if(repo.existsById(e.getEmpId())) {
				//calculation
				double esal =e.getEmpSal();
				double hra =esal*20/100.0;
				double da =esal*10/100.0;
				e.setHra(hra);
				e.setDa(da);
				
				repo.save(e);		
		}else
		{
				//throw EmployeeNotFoundException
			throw new EmployeeNotFoundException("Employee'"+e.getEmpId()+"'  not Exist");
			
		}
	}

	@Override
	public Page<Employee> getAllEmployees(Pageable pageable) {

		 Page<Employee> page = repo.findAll(pageable);
		
		return page  ;
	}

	@Override
	public boolean isEmployeeExitsByEmail(String email) {
		
		
		return repo.getEmailCount(email) >0;
	}

	


	
	
	
	
	
	
	
}
