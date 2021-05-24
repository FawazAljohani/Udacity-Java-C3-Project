package com.udacity.jdnd.course3.critter.Services;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    // save new customer:
    public Customer save(Customer newCustomer, List<Long> petIds) {

        // iterate through petIDs, get pet by Id
        // add each pet to new customer
        List<Pet> pets = new ArrayList<>();

        if (petIds != null) {
            pets = petIds.stream().map(petId -> petRepository.getOne(petId)).collect(toList());
        }
        newCustomer.setPets(pets);

        // save new customer to Customer db:
        return customerRepository.save(newCustomer);
    }

    // find all customers:
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    // find owner by pet ID:
    public Customer findOwnerByPetId(Long petId) {
        Pet pet = petRepository.getOne(petId);

        Customer owner = pet.getOwner();

        return owner;
    }
}
