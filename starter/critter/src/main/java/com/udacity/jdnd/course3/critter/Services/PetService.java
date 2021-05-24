package com.udacity.jdnd.course3.critter.Services;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    // save new pet:
    public Pet save(Pet newPet, Long ownerId) {

        // find owner by Id:
        Customer owner = customerRepository.getOne(ownerId);
        // set pet's owner:
        newPet.setOwner(owner);

        // save new pet to Pet db:
        petRepository.save(newPet);

        // set owner's new pet:
        owner.getPets().add(newPet);
        customerRepository.save(owner);

        // return new pet:
        return newPet;
    }

    // get pet by its Id:
    public Pet findPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    // get all pets:
    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    // find by by its owner Id:
    public List<Pet> findPetsByOwnerId(Long ownerId) {
        Customer owner = customerRepository.getOne(ownerId);

        List<Pet> pets = owner.getPets();

        return pets;
    }
}
