package com.udacity.jdnd.course3.critter.Controller;

import com.udacity.jdnd.course3.critter.Dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.Dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.Dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Services.CustomerService;
import com.udacity.jdnd.course3.critter.Services.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/user")
@Transactional
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        // initialize new customer:
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        // retrieve petIds from Customer DTO
        // set it to new customer:
        List<Long> petIds = customerDTO.getPetIds();

        // return converted DTO with customer and petIDs:
        return convertToDTO(customerService.save(customer, petIds));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        // find all customers using Service layer:
        List<Customer> customers = customerService.findAllCustomer();

        // return as a List of Customer DTO:
        return customers.stream().map(this::convertToDTO).collect(toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertToDTO(customerService.findOwnerByPetId(petId));
    }

    // utility function to convert Customer to CustomerDTO:
    public CustomerDTO convertToDTO(Customer customer) {

        // copy properties of Customer to CustomerDTO:
        CustomerDTO customerDto = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDto);

        // get petIds from Customer, then set to Customer DTO's petIds:
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(toList());
        customerDto.setPetIds(petIds);

        // return customer DTO:
        return customerDto;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        // initialize new Employee:
        // set each properties of Employee using Employee DTO:
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());

        // return converted Employee DTO:
        return convertToDTO(employeeService.save(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertToDTO(employeeService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setDayAvailableByEmployeeId(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        // find all employees with available skills and date inquired:
        List<Employee> employees = employeeService.findEmployeesWithSkillsAndDate(employeeDTO.getSkills(), employeeDTO.getDate());

        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // utility function to convert Employee to Employee DTO:
    public EmployeeDTO convertToDTO(Employee employee) {

        // initialize Employee:
        EmployeeDTO employeeDto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDto);

        return employeeDto;
    }
}
