package com.lapinskyi.universitytest.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "departments")
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @PositiveOrZero(message = "Department id can't be negative")
    private int id;
    
    @Column(name = "name")
    @NotNull(message = "Department name can't be null")
    @Pattern(regexp = "\\S{2,}.*", message = "Department name must have at least two symbols and start with non-white space")
    private String name;
    
    @OneToOne
    @JoinColumn(name = "head_lector_id")
    @Valid
    private Lector departmentHead;    
    
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "departments_lectors",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id"))
    private List<@Valid Lector> lectors;

    public Department() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lector getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(Lector departmentHead) {
        if (lectors.contains(departmentHead)) {
            this.departmentHead = departmentHead;
        }
        else {
            throw new IllegalArgumentException("The given department head was not added to department lectors list.");
        }
    }

    public List<Lector> getLectors() {
        return lectors;
    }

    public void setLectors(List<Lector> lectors) {
        this.lectors = lectors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentHead, id, lectors, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        return Objects.equals(departmentHead, other.departmentHead) && id == other.id
                && Objects.equals(lectors, other.lectors) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + ", departmentHead=" + departmentHead + "]";
    }
}
