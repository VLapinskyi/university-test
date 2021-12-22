package com.lapinskyi.universitytest.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "lectors")
public class Lector {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @PositiveOrZero(message = "Lector id can't be negative")
    private int id;
    
    @Column(name = "first_name")
    @NotNull(message = "Lector first name can't be null")
    @Pattern(regexp = "\\S{2,}.*", message = "Lector first name must have at least two symbols and start with non-white space")
    private String firstName;
    
    @Column(name = "last_name")
    @NotNull(message = "Lector last name can't be null")
    @Pattern(regexp = "\\S{2,}.*", message = "Lector last name must have at least two symbols and start with non-white space")
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "degree")
    @NotNull(message = "Lector degree can't be null")
    private Degree degree;
    
    @Column(name = "salary")
    @PositiveOrZero (message = "Salary can't be negative")
    private int salary;

    public Lector() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degree, firstName, id, lastName, salary);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lector other = (Lector) obj;
        return degree == other.degree && Objects.equals(firstName, other.firstName) && id == other.id
                && Objects.equals(lastName, other.lastName) && salary == other.salary;
    }

    @Override
    public String toString() {
        return "Lector [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", degree=" + degree
                + ", salary=" + salary + "]";
    }
}
