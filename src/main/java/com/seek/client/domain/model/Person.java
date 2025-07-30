package com.seek.client.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a generic person in the system.
 * This base class holds common attributes such as ID, name, and birthdate.
 * It is intended to be extended by other domain models such as {@link Client}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Person {

    protected Long id;
    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;

    /**
     * Calculates the current age of the person based on their birthdate.
     * @return the age in years
     */
    public int getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
}