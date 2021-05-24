package com.udacity.jdnd.course3.critter.Entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private PetType type;

    @Nationalized // similar to UTF-8
    private String name;

    private LocalDate birthDate;

    @Column(nullable = true)
    private String notes;

    // Pet and Customer has M:1 relationship:
    @ManyToOne(fetch=FetchType.EAGER) // many pets can belong to 1 owner
    @JoinColumn(name="customer_id", nullable=false) // map the joined column
    private Customer customer;

    public Pet () {}

    public Pet(PetType type, String name, LocalDate birthDate, String notes) {
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getOwner() {
        return customer;
    }

    public void setOwner(Customer customer) {
        this.customer = customer;
    }
}
