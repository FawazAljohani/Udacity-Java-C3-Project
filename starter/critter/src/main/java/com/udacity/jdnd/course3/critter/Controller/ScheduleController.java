package com.udacity.jdnd.course3.critter.Controller;

import com.udacity.jdnd.course3.critter.Dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@Transactional
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        // initialize new Schedule:
        Schedule schedule = new Schedule();
        // set date and activities properties for Schedule retrieved from schedule DTO
        // petsId and employeeIds properties are set in the ScheduleService .save():
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        // find list of employees by employeesIds retrieved from ScheduleDTO
        // save all employees into Schedule db:
        List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        // find list of pet by petIds retrieved from ScheduleDTO
        // save all pets into Pet db:
        List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
        schedule.setPets(pets);

        // return successfully saved schedule as DTO:
        return convertToDTO(scheduleService.save(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        // find a list of Schedule:
        List<Schedule> schedules = scheduleService.findAllSchedules();

        // return as a list of converted schedule DTO:
        return schedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        // get a list of schedules of a pet using pet id:
        List<Schedule> petSchedules = scheduleService.findSchedulesByPetId(petId);

        // return a list of schedule dto containing of a pet:
        return petSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        // get a list of Schedules of an employee by employee id:
        List<Schedule> employeeSchedules = scheduleService.findSchedulesByEmployeeId(employeeId);
        // return as a list of Schedule DTO of an employee:
        return employeeSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        // find a list of schedules using customer Id:
        List<Schedule> ownerSchedules = scheduleService.findScheduleByOwnerId(customerId);

        // return as a list of Schedule dto of a customer:
        return ownerSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // utility function to convert Schedule to ScheduleDTO:
    public ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        // set List of pet by its id:
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));

        return scheduleDTO;
    }
}
