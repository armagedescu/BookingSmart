package org.filipski.schema;

import jakarta.persistence.*;

@Entity
@Table
        (
                name = "Tester",
                uniqueConstraints =
                {@UniqueConstraint(columnNames = {"name", "surname"})}
        )
public class Tester {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private
    String name;
    @Column(nullable = false)
    private
    String surname;


    @SuppressWarnings("unused")
    public Tester(){}
    public Tester(String name, String surname){
        this.setName(name);
        this.setSurname(surname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}




